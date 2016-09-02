package edu.pitt.dbmi.ccd.rest.client.service.algo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmParamRequest;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobRequestInfo;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;

/**
 * 
 * Aug 24, 2016 6:04:54 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class AlgorithmService extends AbstractRequestService implements AbstractAlgorithmRequest {

    public AlgorithmService(RestHttpsClient restHttpsClient, String username,
	    String scheme, String hostname, int port) {
	super(restHttpsClient, username, scheme, hostname, port);
    }

    public Set<AlgorithmInfo> listAllAlgorithms() throws URISyntaxException,
    ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
		.setScheme(scheme)
		.setPath("/" + REST_API + "/" + username + "/" + ALGORITHMS)
		.setPort(port);

	URI uri = uriBuilder.build();
	HttpGet httpget = new HttpGet(uri);
	httpget.addHeader(HttpHeaders.ACCEPT, "application/json");

	System.out.println("Executing request " + httpget.getRequestLine());
	CloseableHttpResponse response = httpClient.execute(httpget,
		localContext);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");

	EntityUtils.consume(entity);

	return JsonUtils.parseJSONArrayToAlgorithmInfos(jsonResponse);
    }
    
    public JobRequestInfo addToRemoteQueue(String AlgorithmName, AlgorithmParamRequest algoParamRequest) throws URISyntaxException,
    ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
		.setScheme(scheme)
		.setPath("/" + REST_API + "/" + username + "/" + JOBS + "/" + AlgorithmName)
		.setPort(port);

	URI uri = uriBuilder.build();
	HttpPost httppost = new HttpPost(uri);
	httppost.addHeader(HttpHeaders.ACCEPT, "application/json");
	
	JSONObject jsonRequest = new JSONObject(algoParamRequest);
	String json = jsonRequest.toString();
	StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
	
	httppost.setEntity(requestEntity);

	System.out.println("Executing request " + httppost.getRequestLine());
	CloseableHttpResponse response = httpClient.execute(httppost,
		localContext);

	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	
	//System.out.println(jsonResponse);
	
	return JsonUtils.parseJSONObjectToJobRequestInfo(jsonResponse);
    }
}
