package edu.pitt.dbmi.ccd.rest.client.service;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * Aug 24, 2016 6:14:52 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 *
 */
public class AbstractRequestService implements RestRequestService {

    protected final String scheme;
    protected final String hostname;
    protected final int port;

    protected final CloseableHttpClient httpClient;
    protected final HttpClientContext localContext;

    public AbstractRequestService(RestHttpsClient restHttpsClient, String scheme, String hostname, int port) {
        this.httpClient = restHttpsClient.getHttpClient();
        this.localContext = restHttpsClient.getLocalContext();
        this.scheme = scheme;
        this.hostname = hostname;
        this.port = port;
    }

    protected CloseableHttpResponse doGet(URI uri) throws ClientProtocolException, IOException {
        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader(HttpHeaders.ACCEPT, "application/json");
        System.out.println("Executing request " + httpget.getRequestLine());
        return httpClient.execute(httpget, localContext);
    }

    protected CloseableHttpResponse doGet(URI uri, JsonWebToken jsonWebToken)
            throws ClientProtocolException, IOException {
        HttpGet httpget = new HttpGet(uri);
        httpget.addHeader(HttpHeaders.ACCEPT, "application/json");
        httpget.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
        System.out.println("Executing request " + httpget.getRequestLine());

        CloseableHttpResponse response = httpClient.execute(httpget);
        /*
		 * System.out.println("----------------------------------------");
		 *
		 * Header[] header = response.getAllHeaders(); for (int i = 0; i <
		 * header.length; i++) { String name = header[i].getName(); String value
		 * = header[i].getValue(); System.out.println(name + ":" + value); }
		 * System.out.println("----------------------------------------");
         */

        return response;
    }

    protected CloseableHttpResponse doPost(URI uri, JsonWebToken jsonWebToken)
            throws ClientProtocolException, IOException {
        return doPost(uri, null, jsonWebToken);
    }

    protected CloseableHttpResponse doPost(URI uri, HttpEntity entity, JsonWebToken jsonWebToken)
            throws ClientProtocolException, IOException {
        HttpPost httppost = new HttpPost(uri);
        httppost.addHeader(HttpHeaders.ACCEPT, "application/json");
        httppost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
        System.out.println("Executing request " + httppost.getRequestLine());
        if (entity != null) {
            httppost.setEntity(entity);
        }

        CloseableHttpResponse response = httpClient.execute(httppost);
        /*
		 * System.out.println("----------------------------------------");
		 *
		 * Header[] header = response.getAllHeaders(); for (int i = 0; i <
		 * header.length; i++) { String name = header[i].getName(); String value
		 * = header[i].getValue(); System.out.println(name + ":" + value); }
		 * System.out.println("----------------------------------------");
         */

        return response;
    }

    protected CloseableHttpResponse doDelete(URI uri, JsonWebToken jsonWebToken)
            throws ClientProtocolException, IOException {
        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.addHeader(HttpHeaders.ACCEPT, "application/json");
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jsonWebToken.getJwt());
        System.out.println("Executing request " + httpDelete.getRequestLine());

        CloseableHttpResponse response = httpClient.execute(httpDelete);

        return response;
    }

}
