package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.RemoteEvent;

import java.io.UnsupportedEncodingException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class RemoteEventPublisherService implements RemoteEventPublisher {

    private static final FutureCallback<HttpResponse> IGNORE_CALLBACK = null;

    @Value("${event.publisher.url}")
    private String PUBLISH_URL;

    private final CloseableHttpAsyncClient httpclient;

    public RemoteEventPublisherService(){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
    }

    @Override
    public void publish(RemoteEvent remoteEvent) {
        notNull(remoteEvent);
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
            HttpPost httpPost = new HttpPost(PUBLISH_URL + "?channel=Account");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);
            httpclient.execute(httpPost, IGNORE_CALLBACK);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
