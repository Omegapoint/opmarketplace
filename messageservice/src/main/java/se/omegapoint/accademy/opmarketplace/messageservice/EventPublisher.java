package se.omegapoint.accademy.opmarketplace.messageservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EventPublisher implements Consumer<Event<DomainEventModel>> {
    CloseableHttpClient httpclient;

    public EventPublisher(EventBus eventBus){
        httpclient = HttpClients.createDefault();
        eventBus.on(Selectors.regex("\\w+"), this);
    }

    @Override
    public void accept(Event<DomainEventModel> event) {
        DomainEventModel domainEvent = event.getData();
        publish(domainEvent);
    }

    private void publish(DomainEventModel domainEvent) {
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(domainEvent));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpPost httpPost = new HttpPost("http://localhost:8001/event");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson);

        try {
            httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}