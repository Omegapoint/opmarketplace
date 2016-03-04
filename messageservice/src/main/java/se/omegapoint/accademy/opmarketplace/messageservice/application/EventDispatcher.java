package se.omegapoint.accademy.opmarketplace.messageservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.DomainEventModel;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

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
            publish(domainEvent, event.getKey().toString());
        } else {
            System.out.printf("Event with type %s was not allowed...%n", domainEvent.getEventType());
        }

    }

    private void publish(DomainEventModel domainEvent, String channel) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(domainEvent));

            URIBuilder uriBuilder = new URIBuilder(endpoint).addParameter("channel", channel);
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            httpAsyncClient.execute(httpPost, null);

            System.out.printf("Event dispatched from channel %s with type %s to URI %s%n",
                    channel, domainEvent.getEventType(), uriBuilder.build());


        } catch (URISyntaxException | UnsupportedEncodingException | JsonProcessingException e) {
            // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}