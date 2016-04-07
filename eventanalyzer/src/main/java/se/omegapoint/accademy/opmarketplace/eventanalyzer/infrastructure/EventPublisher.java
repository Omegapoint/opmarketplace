package se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.RemoteCommand;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.Command;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableFeatureDTO;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

public class EventPublisher implements Consumer<Event<Command>> {

    @Value("${url.apigateway.command}")
    private URL commandURL;

    CloseableHttpAsyncClient httpAsyncClient;

    public EventPublisher(EventBus eventBus) {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
        eventBus.on(Selectors.object("command"), this);
    }

    @Override
    public void accept(Event<Command> event) {
        Command disableEvent = event.getData();
        publish(new RemoteCommand(disableEvent));
    }

    private void publish(RemoteCommand remoteEvent) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));

            HttpPost httpPost = new HttpPost(commandURL.toURI());

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            // TODO: 04/04/16 change to synch client
            httpAsyncClient.execute(httpPost, null);

            System.out.printf("Command: %s sent to %s%n", remoteEvent.type, commandURL);
            System.out.println(new ObjectMapper().writeValueAsString(remoteEvent));

        } catch (URISyntaxException | UnsupportedEncodingException | JsonProcessingException e) {
            // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}
