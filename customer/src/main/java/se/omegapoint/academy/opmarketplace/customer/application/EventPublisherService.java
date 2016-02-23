package se.omegapoint.academy.opmarketplace.customer.application;
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
import se.omegapoint.academy.opmarketplace.customer.application.json_representations.DomainEventModel;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;


import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class EventPublisherService implements Consumer<Event<DomainEvent>> {
    CloseableHttpClient httpclient;

    public EventPublisherService(EventBus eventBus){
        httpclient = HttpClients.createDefault();
        eventBus.on(Selectors.regex("\\w+"), this);
    }

    @Override
    public void accept(Event<DomainEvent> event) {
        DomainEvent domainEvent = event.getData();
        publish(domainEvent);
    }

    private void publish(DomainEvent domainEvent) {
        DomainEventModel eventModel = new DomainEventModel(domainEvent.aggregateId(), domainEvent.aggregateName(), domainEvent.eventType(), domainEvent.eventData(), domainEvent.time());
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(eventModel));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("http://localhost:8000/event");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson);
        try {
            httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
