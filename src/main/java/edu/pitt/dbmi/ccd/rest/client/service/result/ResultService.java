package edu.pitt.dbmi.ccd.rest.client.service.result;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.ResultFile;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;

/**
 * 
 * Sep 26, 2016 1:26:23 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class ResultService extends AbstractRequestService implements AbstractResultRequest {

	public ResultService(RestHttpsClient restHttpsClient, String scheme, String hostname, int port) {
		super(restHttpsClient, scheme, hostname, port);
	}

	public Set<ResultFile> listAlgorithmResultFiles(JsonWebToken jsonWebToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder().setHost(hostname).setScheme(scheme)
				.setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + RESULTS).setPort(port);

		URI uri = uriBuilder.build();
		CloseableHttpResponse response = doGet(uri, jsonWebToken);

		HttpEntity entity = response.getEntity();
		String jsonResponse = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonResponse);

		EntityUtils.consume(entity);

		return JsonUtils.parseJSONArrayToResultFiles(jsonResponse);
	}

	public String downloadAlgorithmResultFile(String fileName, JsonWebToken jsonWebToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder().setHost(hostname).setScheme(scheme)
				.setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + RESULTS + "/" + fileName)
				.setPort(port);

		URI uri = uriBuilder.build();
		CloseableHttpResponse response = doGet(uri, jsonWebToken);

		HttpEntity entity = response.getEntity();
		String jsonResponse = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonResponse);

		EntityUtils.consume(entity);

		return jsonResponse;
	}

	public Set<ResultFile> listAlgorithmResultComparisonFiles(JsonWebToken jsonWebToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder().setHost(hostname).setScheme(scheme)
				.setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + RESULTS + "/" + COMPARISONS)
				.setPort(port);

		URI uri = uriBuilder.build();
		CloseableHttpResponse response = doGet(uri, jsonWebToken);

		HttpEntity entity = response.getEntity();
		String jsonResponse = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonResponse);

		EntityUtils.consume(entity);

		return JsonUtils.parseJSONArrayToResultFiles(jsonResponse);
	}

	public String downloadAlgorithmResultsComparisonFile(String fileName, JsonWebToken jsonWebToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder().setHost(hostname).setScheme(scheme).setPath(
				"/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + RESULTS + "/" + COMPARISONS + "/" + fileName)
				.setPort(port);

		URI uri = uriBuilder.build();
		CloseableHttpResponse response = doGet(uri, jsonWebToken);

		HttpEntity entity = response.getEntity();
		String jsonResponse = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonResponse);

		EntityUtils.consume(entity);

		return jsonResponse;
	}

	public String compareAlgorithmResults(String fileNames, JsonWebToken jsonWebToken)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder().setHost(hostname).setScheme(scheme).setPath(
				"/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + RESULTS + "/" + COMPARE + "/" + fileNames)
				.setPort(port);

		URI uri = uriBuilder.build();
		CloseableHttpResponse response = doGet(uri, jsonWebToken);

		HttpEntity entity = response.getEntity();
		String jsonResponse = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonResponse);

		EntityUtils.consume(entity);

		return jsonResponse;
	}

}
