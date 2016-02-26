package se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.CommandEvent;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class EventPublisher implements Consumer<Event<CommandEvent>> {
    CloseableHttpAsyncClient httpAsyncClient;

    public EventPublisher(EventBus eventBus) {
        httpAsyncClient = HttpAsyncClients.createDefault();
        httpAsyncClient.start();
        eventBus.on(Selectors.object("command"), this);
    }

    @Override
    public void accept(Event<CommandEvent> event) {
        CommandEvent commandEvent = event.getData();
        publish(commandEvent);
    }

    private void publish(CommandEvent commandEvent) {
        try {
            StringEntity eventJson = new StringEntity(new ObjectMapper().writeValueAsString(commandEvent));

            URIBuilder uriBuilder = new URIBuilder("http://localhost:8000/command").addParameter("token", "kebabpizza");
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(eventJson);

            httpAsyncClient.execute(httpPost, null);

            System.out.printf("Command event dispatched URI %s%n", uriBuilder.build());

        } catch (URISyntaxException | UnsupportedEncodingException | JsonProcessingException e) {
            // TODO: Var detta ok?
            e.printStackTrace();
        }
    }
}
