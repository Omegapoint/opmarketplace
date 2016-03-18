package se.omegapoint.accademy.opmarketplace.messageservice.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;
import se.omegapoint.accademy.opmarketplace.messageservice.infrastructure.EventMetaData;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public class EventDispatcher implements Consumer<Event<String>> {

    private final CloseableHttpAsyncClient httpAsyncClient;
    private final RuleEngine ruleEngine;
    private final URL endpoint;

    public EventDispatcher(RuleEngine ruleEngine, URL endpoint) {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
        this.ruleEngine = ruleEngine;
        this.endpoint = endpoint;
    }

    @Override
    public void accept(Event<String> event) {
        String domainEvent = event.getData();

        EventMetaData metaData = new EventMetaData("UnknownType");
        try {
             metaData = new ObjectMapper().readValue(domainEvent, EventMetaData.class);
        }catch (Exception e) {
            System.err.println("Could not extract type from event.");
        }

        if (ruleEngine.allow(metaData)) {
            publish(domainEvent, metaData, event.getKey().toString());
        } else {
            System.out.printf("Event with type %s was not allowed...%n", metaData.type);
        }

    }

    private void publish(String domainEvent, EventMetaData metaData, String channel) {
        try {
            StringEntity eventJson = new StringEntity(domainEvent);

            URIBuilder uriBuilder = new URIBuilder(endpoint.toURI()).addParameter("channel", channel);
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            httpAsyncClient.execute(httpPost, null);

            System.out.printf("Event dispatched from channel %s with type %s to URI %s%n",
                    channel, metaData.type, uriBuilder.build());


        } catch (URISyntaxException | UnsupportedEncodingException e) {
            // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}
