package edu.pitt.dbmi.ccd.rest.client.service.algo;

import edu.pitt.dbmi.ccd.rest.client.RestHttpsClient;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.AbstractRequestService;
import edu.pitt.dbmi.ccd.rest.client.util.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * Aug 24, 2016 6:04:54 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 *
 */
public class AlgorithmService extends AbstractRequestService implements AbstractAlgorithmRequest {

    public AlgorithmService(RestHttpsClient restHttpsClient,
            String scheme, String hostname, int port) {
        super(restHttpsClient, scheme, hostname, port);
    }

    public Set<AlgorithmInfo> listAllAlgorithms(JsonWebToken jsonWebToken) throws URISyntaxException,
            ClientProtocolException, IOException {
        URIBuilder uriBuilder = new URIBuilder().setHost(hostname)
                .setScheme(scheme)
                .setPath("/" + REST_API + "/" + jsonWebToken.getUserId() + "/" + ALGORITHMS)
                .setPort(port);

        URI uri = uriBuilder.build();
        CloseableHttpResponse response = doGet(uri, jsonWebToken);

        HttpEntity entity = response.getEntity();
        String jsonResponse = EntityUtils.toString(entity, "UTF-8");

        EntityUtils.consume(entity);

        return JsonUtils.parseJSONArrayToAlgorithmInfos(jsonResponse);
    }

}
