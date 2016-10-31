package edu.pitt.dbmi.ccd.rest.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * 
 * Aug 20, 2016 12:17:02 AM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */

public class RestHttpsClient {

    private final String email;
    private final String password;
    private final String scheme;
    private final String hostname;
    private final int port;

    private final CloseableHttpClient httpClient;
    private final HttpClientContext localContext;
    
    public RestHttpsClient(String email, String password, String scheme,
	    String hostname, int port) throws Exception {
	this.email = email;
	this.password = password;
	this.scheme = scheme;
	this.hostname = hostname;
	this.port = port;
	this.httpClient = createHttpClient();
	this.localContext = establishCustomContext(httpClient);
    }

    private CloseableHttpClient createHttpClient() throws Exception {
	// Trust own CA and all self-signed certs
	SSLContextBuilder builder = new SSLContextBuilder();
	builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		builder.build(),
		new String[] { "TLSv1.2" },
		null,
		SSLConnectionSocketFactory.getDefaultHostnameVerifier());

	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	credsProvider.setCredentials(new AuthScope(hostname, port),
		new UsernamePasswordCredentials(email, password));

	CloseableHttpClient httpClient = HttpClients.custom()
		.setSSLSocketFactory(sslsf)
		.setDefaultCredentialsProvider(credsProvider).build();

	return httpClient;
    }

    private HttpClientContext establishCustomContext(
	    CloseableHttpClient httpclient) throws Exception {
	HttpHost target = new HttpHost(hostname, port, scheme);

	// Create AuthCache instance
	AuthCache authCache = new BasicAuthCache();
	// Generate BASIC scheme object and add it to the local
	// auth cache
	BasicScheme basicAuth = new BasicScheme();
	authCache.put(target, basicAuth);

	// Add AuthCache to the execution context
	HttpClientContext localContext = HttpClientContext.create();
	localContext.setAuthCache(authCache);
	
	return localContext;
    }

    public CloseableHttpClient getHttpClient() {
	return httpClient;
    }

    public HttpClientContext getLocalContext() {
	return localContext;
    }

    public static void main(String[] args) throws Exception {

    }
}
