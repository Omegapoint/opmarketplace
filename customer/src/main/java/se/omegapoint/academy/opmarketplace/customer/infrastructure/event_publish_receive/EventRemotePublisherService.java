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
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.RemoteEvent;

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
    public void publish(DomainEvent event) {
        notNull(event);
        if (event instanceof AccountCreated)
            publish(new RemoteEvent(new AccountCreatedModel((AccountCreated)event), AccountCreatedModel.TYPE));
        else if (event instanceof AccountUserChanged)
            publish(new RemoteEvent(new AccountUserChangedModel((AccountUserChanged)event), AccountUserChangedModel.TYPE));

        //TODO [dd] what happens if we get an event that doesn't match? Is it a bug? Missing feature? Add contract!
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
