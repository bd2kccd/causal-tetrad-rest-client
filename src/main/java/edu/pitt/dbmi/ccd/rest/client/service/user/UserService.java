package edu.pitt.dbmi.ccd.rest.client.service.user;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;

/**
 * 
 * Oct 18, 2016 4:12:07 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class UserService extends AbstractRequestService implements
	AbstractUserRequest {

    public UserService(RestHttpsClient restHttpsClient, String scheme,
	    String hostname, int port) {
	super(restHttpsClient, scheme, hostname, port);
    }

    public JsonWebToken requestJWT() throws URISyntaxException,
	    ClientProtocolException, IOException {
	URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
		.setScheme(scheme).setPath("/" + REST_API + "/" + JWT)
		.setPort(port);

	URI uri = uriBuilder.build();
	//HttpGet httpget = new HttpGet(uri);
	//httpget.addHeader(HttpHeaders.ACCEPT, "application/json");

	//System.out.println("Executing request " + httpget.getRequestLine());
	CloseableHttpResponse response = doGet(uri);//httpClient.execute(httpget,
		//localContext);
	
	HttpEntity entity = response.getEntity();
	String jsonResponse = EntityUtils.toString(entity, "UTF-8");
	
	System.out.println(jsonResponse);
	
	return JsonUtils.parseJSONObjectToJsonWebToken(jsonResponse);
    }

}
