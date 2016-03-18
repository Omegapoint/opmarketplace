package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.RemoteEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class EventRemotePublisherService implements EventPublisher {

    private static final FutureCallback<HttpResponse> IGNORE_CALLBACK = null;
    @Value("${event.publisher.url}")
    private String publisherURL;

    private final CloseableHttpAsyncClient httpclient;

    public EventRemotePublisherService(CloseableHttpAsyncClient httpclient){
        this.httpclient = notNull(httpclient);
    }

    public void cleanup() throws IOException {
        httpclient.close();
    }

    @Override
    public void publish(AccountCreated event) {
        publish(new RemoteEvent(new AccountCreatedModel(event)));
    }

    @Override
    public void publish(AccountNotCreated event) {
        publish(new RemoteEvent(new AccountNotCreatedModel(event)));
    }

    @Override
    public void publish(AccountObtained event) {
        publish(new RemoteEvent(new AccountObtainedModel(event)));
    }

    @Override
    public void publish(AccountUserChanged event) {
        publish(new RemoteEvent(new AccountUserChangedModel(event)));
    }

    @Override
    public void publish(AccountNotObtained event) {
        publish(new RemoteEvent(new AccountNotObtainedModel(event)));
    }

    @Override
    public void publish(AccountUserNotChanged event) {
        publish(new RemoteEvent(new AccountUserNotChangedModel(event)));
    }

    private void publish(RemoteEvent remoteEvent) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
            HttpPost httpPost = new HttpPost(publisherURL + "?channel=Account");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);
            httpclient.execute(httpPost, IGNORE_CALLBACK);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace(); //TODO [dd] not ok to print stack trace and continue.
        }
    }
}
