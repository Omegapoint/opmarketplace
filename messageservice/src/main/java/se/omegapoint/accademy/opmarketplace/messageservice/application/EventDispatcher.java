package se.omegapoint.accademy.opmarketplace.messageservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.DomainEventModel;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;

import java.io.UnsupportedEncodingException;

public class EventDispatcher implements Consumer<Event<DomainEventModel>> {

    private final CloseableHttpAsyncClient httpAsyncClient;
    private final RuleEngine ruleEngine;
    private final String endpoint;

    public EventDispatcher(RuleEngine ruleEngine, String endpoint) {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
        this.ruleEngine = ruleEngine;
        this.endpoint = endpoint;
    }

    @Override
    public void accept(Event<DomainEventModel> event) {
        DomainEventModel domainEvent = event.getData();
        if (ruleEngine.allow(domainEvent)) {
            publish(domainEvent);
        }

        System.out.printf("Event dispatched from channel %s with type %s to endpoint %s%n",
                event.getKey(), domainEvent.getEventType(), endpoint);
    }

    private void publish(DomainEventModel domainEvent) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(domainEvent));

            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            httpAsyncClient.execute(httpPost, null);

        } catch (UnsupportedEncodingException | JsonProcessingException e) { // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}
