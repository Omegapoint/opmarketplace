package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.*;

import java.io.UnsupportedEncodingException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class EventRemotePublisherService implements EventPublisher {

    private static final FutureCallback<HttpResponse> IGNORE_CALLBACK = null;
    @Value("${event.publisher.url}")
    private String publisherURL;

    private final CloseableHttpAsyncClient httpclient;

    //TODO [dd] potential resource leakage. When is the http client closed?

    // TODO: 14/03/16 Inject HttpClient
    public EventRemotePublisherService(){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
    }

    @Override
    public void publish(AccountCreated event) {
        publish(new RemoteEvent(new AccountCreatedModel(event), AccountCreatedModel.TYPE));
    }

    @Override
    public void publish(AccountNotCreated event) {
        publish(new RemoteEvent(new AccountNotCreatedModel(event), AccountNotCreatedModel.TYPE));
    }

    @Override
    public void publish(AccountObtained event) {
        publish(new RemoteEvent(new AccountObtainedModel(event), AccountObtainedModel.TYPE));
    }

    @Override
    public void publish(AccountUserChanged event) {
        publish(new RemoteEvent(new AccountUserChangedModel(event), AccountUserChangedModel.TYPE));
    }

    @Override
    public void publish(AccountNotObtained event) {
        publish(new RemoteEvent(new AccountNotObtainedModel(event), AccountNotObtainedModel.TYPE));
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
            // TODO: 14/03/16 throw illegalstateexception
        }
    }
}
