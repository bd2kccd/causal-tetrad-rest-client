package edu.pitt.dbmi.ccd.rest.client.service;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;

/**
 * 
 * Aug 24, 2016 6:14:52 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class AbstractRequestService implements RestRequestService {

    protected final String username;// = "kong";
    protected final String scheme;// = "http";// "https";
    protected final String hostname;// = "ccd1.vm.bridges.psc.edu";// "54.237.230.113";//
    protected final int port;// = 49153;// 443;

    protected final CloseableHttpClient httpClient;
    protected final HttpClientContext localContext;

    public AbstractRequestService(RestHttpsClient restHttpsClient,
	    String username, String scheme, String hostname, int port) {
	this.httpClient = restHttpsClient.getHttpClient();
	this.localContext = restHttpsClient.getLocalContext();
	this.username = username;
	this.scheme = scheme;
	this.hostname = hostname;
	this.port = port;
    }
}
