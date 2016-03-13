package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.RemoteEvent;

import java.io.UnsupportedEncodingException;


public class EventRemotePublisherService {
    CloseableHttpAsyncClient httpclient;

    public EventRemotePublisherService(){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
    }

    public void publish(RemoteEvent remoteEvent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("http://localhost:8000/event?channel=Account");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson);

        httpclient.execute(httpPost, null);
    }
}
