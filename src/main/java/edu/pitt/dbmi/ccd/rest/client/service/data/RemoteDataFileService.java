package edu.pitt.dbmi.ccd.rest.client.service.data;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;

/**
 * 
 * Aug 23, 2016 7:10:11 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class RemoteDataFileService extends AbstractRequestService {

    public RemoteDataFileService(RestHttpsClient restHttpsClient,
	    String scheme, String hostname, int port) {
	super(restHttpsClient, scheme, hostname, port);
    }

    public Set<DataFile> retrieveDataFileInfo(JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {

	URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
		.setScheme(scheme)
		.setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + DATASET)
		.setPort(port);

	URI uri = uriBuilder.build();
	/*HttpGet httpget = new HttpGet(uri);
	httpget.addHeader(HttpHeaders.ACCEPT, "application/json");
	httpget.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
	System.out.println("Executing request " + httpget.getRequestLine());*/
	CloseableHttpResponse response = doGet(uri, jsonWebToken);//httpClient.execute(httpget);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);
	/*System.out.println("----------------------------------------");

	Header[] header = response.getAllHeaders();
	for (int i = 0; i < header.length; i++) {
	    String name = header[i].getName();
	    String value = header[i].getValue();
	    System.out.println(name + ":" + value);
	}
	System.out.println("----------------------------------------");*/

	EntityUtils.consume(entity);

	return JsonUtils.parseJSONArrayToDataFiles(jsonResponse);
    }

    public DataFile summarizeDataFile(long id, String variableType,
	    String fileDelimiter, JsonWebToken jsonWebToken) throws URISyntaxException,
	    ClientProtocolException, IOException {

	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + DATASET + "/"
				+ SUMMARIZE).setPort(port);

	URI uri = uriBuilder.build();
	/*HttpPost httppost = new HttpPost(uri);
	httppost.addHeader(HttpHeaders.ACCEPT, "application/json");
	httppost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());*/

	JSONObject jsonRequest = new JSONObject();
	jsonRequest.put("id", id);
	jsonRequest.put("variableType", variableType);
	jsonRequest.put("fileDelimiter", fileDelimiter);
	String json = jsonRequest.toString();
	System.out.println(json);
	StringEntity requestEntity = new StringEntity(json,
		ContentType.APPLICATION_JSON);

	//httppost.setEntity(requestEntity);

	//System.out.println("Executing request " + httppost.getRequestLine());
	CloseableHttpResponse response = doPost(uri, requestEntity, jsonWebToken);//httpClient.execute(httppost);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);
	
	EntityUtils.consume(entity);

	return JsonUtils.parseJSONObjectToDataFile(jsonResponse);
    }

    public Set<DataFile> retrievePriorKnowledgeFileInfo(JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {

	URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
		.setScheme(scheme)
		.setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + PRIOR_KNOWLEDGE)
		.setPort(port);

	URI uri = uriBuilder.build();
	/*HttpGet httpget = new HttpGet(uri);
	httpget.addHeader(HttpHeaders.ACCEPT, "application/json");
	httpget.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
	System.out.println("Executing request " + httpget.getRequestLine());*/
	CloseableHttpResponse response = doGet(uri, jsonWebToken);//httpClient.execute(httpget);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);
	/*System.out.println("----------------------------------------");

	Header[] header = response.getAllHeaders();
	for (int i = 0; i < header.length; i++) {
	    String name = header[i].getName();
	    String value = header[i].getValue();
	    System.out.println(name + ":" + value);
	}
	System.out.println("----------------------------------------");*/

	EntityUtils.consume(entity);

	return JsonUtils.parseJSONArrayToDataFiles(jsonResponse);
    }


}
