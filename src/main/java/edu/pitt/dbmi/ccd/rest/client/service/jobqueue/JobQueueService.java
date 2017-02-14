package edu.pitt.dbmi.ccd.rest.client.service.jobqueue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmParamRequest;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;

/**
 * 
 * Sep 22, 2016 2:58:48 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class JobQueueService extends AbstractRequestService implements
	AbstractJobQueueRequest {

    public JobQueueService(RestHttpsClient restHttpsClient, String scheme,
	    String hostname, int port) {
	super(restHttpsClient, scheme, hostname, port);
    }

    public JobInfo addToRemoteQueue(String AlgorithmName,
	    AlgorithmParamRequest algoParamRequest, JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/"
				+ JOBS + "/" + AlgorithmName).setPort(port);

	URI uri = uriBuilder.build();

	JSONObject jsonRequest = new JSONObject(algoParamRequest);
	String json = jsonRequest.toString();
	StringEntity requestEntity = new StringEntity(json,
		ContentType.APPLICATION_JSON);

	CloseableHttpResponse response = doPost(uri, requestEntity,
		jsonWebToken);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");

	System.out.println(jsonResponse);

	return JsonUtils.parseJSONObjectToJobInfo(jsonResponse);
    }

    public List<JobInfo> getActiveJobs(JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/"
				+ JOBS).setPort(port);

	URI uri = uriBuilder.build();
	CloseableHttpResponse response = doGet(uri, jsonWebToken);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);

	EntityUtils.consume(entity);

	return JsonUtils.parseJSONArrayToJobInfos(jsonResponse);
    }

    public JobInfo getJobStatus(long id, JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/"
				+ JOBS + "/" + id).setPort(port);

	URI uri = uriBuilder.build();
	CloseableHttpResponse response = doGet(uri, jsonWebToken);

	StatusLine sl = response.getStatusLine();
	if (sl.getStatusCode() == 404) {
	    return null;
	}

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);

	EntityUtils.consume(entity);

	return JsonUtils.parseJSONObjectToJobInfo(jsonResponse);
    }

    public void requestJobKilled(long id, JsonWebToken jsonWebToken)
	    throws URISyntaxException, ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder()
		.setHost(hostname)
		.setScheme(scheme)
		.setPath(
			"/" + REST_API + "/" + jsonWebToken.getUserId() + "/"
				+ JOBS + "/" + id).setPort(port);

	URI uri = uriBuilder.build();
	CloseableHttpResponse response = doDelete(uri, jsonWebToken);

	StatusLine sl = response.getStatusLine();
	if (sl.getStatusCode() == 404) {
	    return;
	}

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	System.out.println(jsonResponse);

	EntityUtils.consume(entity);
    }

}
