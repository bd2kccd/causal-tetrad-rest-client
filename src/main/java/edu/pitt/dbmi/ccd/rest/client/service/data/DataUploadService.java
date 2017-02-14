package edu.pitt.dbmi.ccd.rest.client.service.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.utils.URIBuilder;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.data.ChunkUpload;
import edu.pitt.dbmi.ccd.rest.client.dto.data.UploadStatus;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;

/**
 * 
 * Aug 23, 2016 7:10:32 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class DataUploadService extends AbstractRequestService {

    private final Map<String, ChunkUpload> fileUploadMap;

    private final ExecutorService executorService;

    private static final long chunkSize = 512 * 1024;

    public DataUploadService(RestHttpsClient restHttpsClient,
	    int simultaneousUpload, String scheme,
	    String hostname, int port) {
	super(restHttpsClient, scheme, hostname, port);
	this.fileUploadMap = new HashMap<>();
	this.executorService = Executors.newFixedThreadPool(simultaneousUpload);
    }

    public synchronized List<UploadStatus> getJobsInQueue() {
	List<UploadStatus> status = new LinkedList<>();

	for (String key : fileUploadMap.keySet()) {
	    status.add(new UploadStatus(key, fileUploadMap.get(key)
		    .isSuspended()));
	}

	return status;
    }

    public synchronized int getUploadJobStatus(String id) {
	ChunkUpload chunkUpload = fileUploadMap.get(id);
	if (chunkUpload == null) {
	    return 100;
	} else {
	    return (int) (chunkUpload.getProgress() * 100);
	}
    }

    public synchronized boolean pauseUploadStatus(String id) {
	ChunkUpload chunkUpload = fileUploadMap.get(id);
	if (chunkUpload == null) {
	    return false;
	} else {
	    chunkUpload.suspend();
	    return true;
	}
    }

    public synchronized boolean startUpload(Path file, JsonWebToken jsonWebToken)
	    throws URISyntaxException {
	String id = file.toAbsolutePath().toString();
	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/" +
				CHUNKUPLOAD).setPort(port);

	URI uri = uriBuilder.build();
	ChunkUpload chunkUpload = new ChunkUpload(file, chunkSize, uri,
		httpClient, jsonWebToken, fileUploadMap);
	fileUploadMap.put(id, chunkUpload);
	executorService.execute(chunkUpload);
	// executorService.shutdown();
	// executorService.awaitTermination(5, TimeUnit.MINUTES);

	return true;
    }
}
