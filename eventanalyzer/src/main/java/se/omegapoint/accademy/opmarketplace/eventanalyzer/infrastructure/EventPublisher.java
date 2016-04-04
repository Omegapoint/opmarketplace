package se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableAccountCreation;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public class EventPublisher implements Consumer<Event<DisableAccountCreation>> {

    @Value("${url.apigateway.command}")
    private URL commandURL;

    CloseableHttpAsyncClient httpAsyncClient;

    public EventPublisher(EventBus eventBus) {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
        eventBus.on(Selectors.object("command"), this);
    }

    @Override
    public void accept(Event<DisableAccountCreation> event) {
        DisableAccountCreation disableEvent = event.getData();
        publish(disableEvent);
    }

    private void publish(DisableAccountCreation disableEvent) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(disableEvent));

            HttpPost httpPost = new HttpPost(commandURL.toURI());

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            // TODO: 04/04/16 change to synch client
            httpAsyncClient.execute(httpPost, null);

            System.out.printf("DisableAccountCreation command sent to %s%n", commandURL);

        } catch (URISyntaxException | UnsupportedEncodingException | JsonProcessingException e) {
            // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}
