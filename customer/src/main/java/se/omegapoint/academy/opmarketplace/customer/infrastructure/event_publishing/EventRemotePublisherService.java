package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.RemoteEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.io.UnsupportedEncodingException;


public class EventRemotePublisherService implements EventPublisher {
    CloseableHttpAsyncClient httpclient;

    public EventRemotePublisherService(){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
    }

    @Override
    public void publish(DomainEvent event) {
        if (event instanceof AccountCreated)
            publish(new RemoteEvent(new AccountCreatedModel((AccountCreated)event), AccountCreated.NAME));
        else if (event instanceof AccountUserChanged)
            publish(new RemoteEvent(new AccountUserChangedModel((AccountUserChanged)event), AccountUserChanged.NAME));
    }

    private void publish(RemoteEvent remoteEvent) {
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("http://localhost:8000/event");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson);

        httpclient.execute(httpPost, null);
    }
}
