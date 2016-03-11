package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.RemoteEvent;

import java.io.UnsupportedEncodingException;


public class EventRemotePublisherService implements EventPublisher {
    //TODO [dd] make immutable
    CloseableHttpAsyncClient httpclient;

    //TODO [dd] potential resource leakage. When is the http client closed?

    public EventRemotePublisherService(){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
    }

    @Override
    public void publish(DomainEvent event) {
        //TODO [dd] add notNull contracts

        if (event instanceof AccountCreated)
            publish(new RemoteEvent(new AccountCreatedModel((AccountCreated)event), AccountCreated.NAME));
        else if (event instanceof AccountUserChanged)
            publish(new RemoteEvent(new AccountUserChangedModel((AccountUserChanged)event), AccountUserChanged.NAME));

        //TODO [dd] what happens if we get an event that doesn't match? Is it a bug? Missing feature? Add contract!
    }

    private void publish(RemoteEvent remoteEvent) {
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace(); //TODO [dd] not ok to print stack trace and continue.
        }
        //TODO [dd]: move this block of code into the try clause.
        HttpPost httpPost = new HttpPost("http://localhost:8000/event");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson); //TODO [dd] will be null if UnsupportedEncodingException | JsonProcessingException

        httpclient.execute(httpPost, null); //TODO [dd] remove magic value and replace it by a constant. Like IGNORE_CALLBACK or similar
    }
}
