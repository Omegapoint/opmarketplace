package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events.*;

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
    public void publish(DomainEvent event, String requestId) {
        if (event instanceof ItemCreated) {
            dispatch(new ItemCreatedDTO((ItemCreated) event, requestId));
        } else if (event instanceof ItemNotCreated) {
            dispatch(new ItemNotCreatedDTO((ItemNotCreated) event, requestId));
        } else if (event instanceof ItemObtained) {
            dispatch(new ItemObtainedDTO((ItemObtained) event, requestId));
        } else if (event instanceof ItemNotObtained) {
            dispatch(new ItemNotObtainedDTO((ItemNotObtained) event, requestId));
        } else {
            throw new IllegalStateException("Domain Event not recognized.");
        }
    }

    private void dispatch(Event eventDTO) {
        try {
            OutgoingRemoteEvent outgoingRemoteEvent = new OutgoingRemoteEvent(eventDTO);
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(outgoingRemoteEvent));
            HttpPost httpPost = new HttpPost(publisherURL + "?channel=Item");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);
            httpclient.execute(httpPost, IGNORE_CALLBACK);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace(); //TODO [dd] not ok to print stack trace and continue.
        }
    }
}
