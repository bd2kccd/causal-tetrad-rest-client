package edu.pitt.dbmi.ccd.rest.client.dto.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;

/**
 * 
 * Aug 22, 2016 7:47:32 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * Optional: make it parallel with its child's Callable implementation
 */
public class ChunkUpload implements Runnable {

    private final Path file;

    private final long chunkSize;

    private final URI uri;

    private final CloseableHttpClient httpclient;

    private JsonWebToken jsonWebToken;

    private double progress;

    private boolean suspended;

    private boolean stopped;

    private String resumableIdentifier;

    private final Map<String, ChunkUpload> fileUploadMap;

    public ChunkUpload(Path file, long chunkSize, URI uri,
	    CloseableHttpClient httpclient, JsonWebToken jsonWebToken,
	    Map<String, ChunkUpload> fileUploadMap) {
	this.file = file;
	this.chunkSize = chunkSize;
	this.uri = uri;
	this.httpclient = httpclient;
	this.jsonWebToken = jsonWebToken;
	this.fileUploadMap = fileUploadMap;
    }

    public void run() {
	progress = 0;
	suspended = false;
	stopped = false;

	try {
	    String fileName = this.file.getFileName().toString();
	    long fileSize = Files.size(this.file);
	    long maxOffset = Math.max(Math.round(fileSize / this.chunkSize), 1);
	    long resumableChunkSize = this.chunkSize;
	    long resumableTotalSize = fileSize;

	    long resumableTotalChunks = maxOffset;
	    this.resumableIdentifier = String.format("%d-%s", fileSize,
		    fileName.replaceAll("/[^0-9a-zA-Z_-]/img", ""));
	    String resumableFilename = fileName;
	    String resumableRelativePath = fileName;
	    String resumableType = Files.probeContentType(this.file);
	    if (resumableType == null || resumableType.length() == 0) {
		resumableType = "Text";
	    }

	    try (BufferedInputStream inputStream = new BufferedInputStream(
		    Files.newInputStream(file))) {

		try {
		    for (long offset = 0; offset < maxOffset; offset++) {

			synchronized (this) {
			    while (suspended) {
				wait();
				if (stopped) {
				    break;
				}
			    }
			    if (stopped) {
				break;
			    }
			}

			long startByte = offset * this.chunkSize;
			long endByte = Math.min(fileSize, (offset + 1)
				* this.chunkSize);
			if (fileSize - endByte < this.chunkSize) {
			    endByte = fileSize;
			}
			long resumableChunkNumber = offset + 1;
			long resumableCurrentChunkSize = endByte - startByte;

			try {
			    List<NameValuePair> nameValuePairs = new ArrayList<>();
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableType", resumableType));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableIdentifier",
				    this.resumableIdentifier));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableChunkNumber", Long
					    .toString(resumableChunkNumber)));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableChunkSize", Long
					    .toString(resumableChunkSize)));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableTotalSize", Long
					    .toString(resumableTotalSize)));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableCurrentChunkSize",
				    Long.toString(resumableCurrentChunkSize)));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableTotalChunks", Long
					    .toString(resumableTotalChunks)));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableFilename", resumableFilename));
			    nameValuePairs.add(new BasicNameValuePair(
				    "resumableRelativePath",
				    resumableRelativePath));

			    URIBuilder uriBuilder = new URIBuilder(this.uri)
				    .setParameters(nameValuePairs);

			    URI uri = uriBuilder.build();
			    HttpGet httpget = new HttpGet(uri);
			    httpget.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
			    //System.out.println("Executing request "
			    //	    + httpget.getRequestLine());
			    CloseableHttpResponse response = httpclient
				    .execute(httpget);

			    int statusCode = response.getStatusLine()
				    .getStatusCode();

			    HttpEntity entity = response.getEntity();
			    EntityUtils.consume(entity);

			    if (statusCode == HttpStatus.SC_NOT_FOUND) {
				int readSize = (int) resumableCurrentChunkSize;
				byte[] byteChunkPart = new byte[readSize];
				inputStream.read(byteChunkPart, 0, readSize);

				MultipartEntityBuilder multiEnBuilder = MultipartEntityBuilder
					.create();
				multiEnBuilder.addPart("file",
					new ByteArrayBody(byteChunkPart,
						fileName));
				multiEnBuilder
					.addPart(
						"resumableChunkNumber",
						new StringBody(
							Long.toString(resumableChunkNumber),
							ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableChunkSize",
					new StringBody(
						Long.toString(chunkSize),
						ContentType.TEXT_PLAIN));
				multiEnBuilder
					.addPart(
						"resumableCurrentChunkSize",
						new StringBody(
							Long.toString(resumableCurrentChunkSize),
							ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableFilename",
					new StringBody(resumableFilename,
						ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableIdentifier",
					new StringBody(
						this.resumableIdentifier,
						ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableRelativePath",
					new StringBody(resumableRelativePath,
						ContentType.TEXT_PLAIN));
				multiEnBuilder
					.addPart(
						"resumableTotalChunks",
						new StringBody(
							Long.toString(resumableTotalChunks),
							ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart(
					"resumableTotalSize",
					new StringBody(Long
						.toString(resumableTotalSize),
						ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableType",
					new StringBody(resumableType,
						ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableFilename",
					new StringBody(resumableFilename,
						ContentType.TEXT_PLAIN));
				multiEnBuilder.addPart("resumableRelativePath",
					new StringBody(resumableRelativePath,
						ContentType.TEXT_PLAIN));

				HttpEntity httpEntity = multiEnBuilder.build();
				HttpPost httppost = new HttpPost(uri);
				httppost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
				httppost.setEntity(httpEntity);
				//System.out.println("Executing request "
				//	+ httppost.getRequestLine());
				response = httpclient.execute(httppost);
				statusCode = response.getStatusLine()
					.getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
				    progress = (offset + 1.0) / maxOffset;
				}
				entity = response.getEntity();
				EntityUtils.consume(entity);
			    }

			} catch (URISyntaxException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

		    }
		} catch (InterruptedException e) {
		    e.printStackTrace(System.err);
		}

	    } catch (IOException e) {
		e.printStackTrace(System.err);
	    }
	    fileUploadMap.remove(this.file.toAbsolutePath().toString());
	} catch (IOException e) {
	    e.printStackTrace(System.err);
	}
    }

    public boolean isSuspended() {
        return suspended;
    }

    public synchronized void suspend() {
        this.suspended = true;
    }

    public synchronized void resume() {
        suspended = false;
        notify();
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        this.stopped = true;
    }

    public double getProgress() {
        return progress;
    }
  
}
