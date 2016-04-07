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
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditDeposited;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.OutgoingRemoteEvent;
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
    public void publish(DomainEvent event, String requestId) {
        if (event instanceof AccountCreated) {
            dispatch(new AccountCreatedDTO((AccountCreated) event, requestId));
        } else if (event instanceof AccountNotCreated) {
            dispatch(new AccountNotCreatedDTO((AccountNotCreated) event, requestId));
        } else if (event instanceof AccountObtained) {
            dispatch(new AccountObtainedDTO((AccountObtained) event, requestId));
        } else if (event instanceof AccountNotObtained) {
            dispatch(new AccountNotObtainedDTO((AccountNotObtained) event, requestId));
        } else if (event instanceof AccountUserChanged) {
            dispatch(new AccountUserChangedDTO((AccountUserChanged) event, requestId));
        } else if (event instanceof AccountUserNotChanged) {
            dispatch(new AccountUserNotChangedDTO((AccountUserNotChanged) event, requestId));
        } else if (event instanceof AccountDeleted) {
            dispatch(new AccountDeletedDTO((AccountDeleted) event, requestId));
        } else if (event instanceof AccountNotDeleted) {
            dispatch(new AccountNotDeletedDTO((AccountNotDeleted) event, requestId));
        } else if (event instanceof AccountCreditDeposited) {
            dispatch(new AccountCreditDepositedDTO((AccountCreditDeposited) event, requestId));
        } else if (event instanceof AccountCreditNotDeposited) {
            dispatch(new AccountCreditNotDepositedDTO((AccountCreditNotDeposited) event, requestId));
        } else if (event instanceof ItemPaymentCompleted) {
            dispatch(new ItemPaymentCompletedDTO((ItemPaymentCompleted) event, requestId));
        } else if (event instanceof ItemPaymentNotCompleted) {
            dispatch(new ItemPaymentNotCompletedDTO((ItemPaymentNotCompleted) event, requestId));
        } else {
            throw new IllegalStateException("Domain Event not recognized.");
        }
    }

    private void dispatch(Event eventDTO) {
        try {
            OutgoingRemoteEvent outgoingRemoteEvent = new OutgoingRemoteEvent(eventDTO);
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(outgoingRemoteEvent));
            HttpPost httpPost = new HttpPost(publisherURL + "?channel=Account");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);
            httpclient.execute(httpPost, IGNORE_CALLBACK);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace(); //TODO [dd] not ok to print stack trace and continue.
        }
    }
}
