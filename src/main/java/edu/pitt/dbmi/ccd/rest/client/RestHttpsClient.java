package edu.pitt.dbmi.ccd.rest.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmParamRequest;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobRequestInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.service.algo.AbstractAlgorithmRequest;
import edu.pitt.dbmi.ccd.rest.client.service.algo.AlgorithmService;
import edu.pitt.dbmi.ccd.rest.client.service.data.DataUploadService;
import edu.pitt.dbmi.ccd.rest.client.service.data.RemoteDataFileService;

/**
 * 
 * Aug 20, 2016 12:17:02 AM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */

public class RestHttpsClient {

    private final String username;// = "kong";
    private final String password;// = "kongman20";
    private final String scheme;// = "http";// "https";
    private final String hostname;// = "54.237.230.113";//
				  // "ccd1.vm.bridges.psc.edu";
    private final int port;// = 49153;// 443;

    private final CloseableHttpClient httpClient;
    private final HttpClientContext localContext;

    public RestHttpsClient(String username, String password, String scheme,
	    String hostname, int port) throws Exception {
	this.username = username;
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
		new String[] { "TLS_RSA_WITH_AES_128_CBC_SHA",
			"TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
			"TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
			"TLS_EMPTY_RENEGOTIATION_INFO_SCSV",
			"SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
			"SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
			"SSL_RSA_WITH_3DES_EDE_CBC_SHA",
			"SSL_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_SHA" },
		SSLConnectionSocketFactory.getDefaultHostnameVerifier());

	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	credsProvider.setCredentials(new AuthScope(hostname, port),
		new UsernamePasswordCredentials(username, password));

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
