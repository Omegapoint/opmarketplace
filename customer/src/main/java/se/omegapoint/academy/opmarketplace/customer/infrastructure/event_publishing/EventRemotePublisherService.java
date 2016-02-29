package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.application.json_representations.AccountCreatedJsonModel;
import se.omegapoint.academy.opmarketplace.customer.application.json_representations.AccountUserChangedJsonModel;
import se.omegapoint.academy.opmarketplace.customer.application.json_representations.RemoteEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.io.UnsupportedEncodingException;


public class EventRemotePublisherService implements Consumer<Event<DomainEvent>> {
    CloseableHttpAsyncClient httpclient;

    public EventRemotePublisherService(EventBus eventBus){
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        eventBus.on(Selectors.regex("\\w+"), this);
    }

    @Override
    public void accept(Event<DomainEvent> event) {
        DomainEvent domainEvent = event.getData();
        if (domainEvent instanceof AccountCreated)
            publish(new RemoteEvent(new AccountCreatedJsonModel((AccountCreated)domainEvent), AccountCreated.NAME));
        else if (domainEvent instanceof AccountUserChanged)
            publish(new RemoteEvent(new AccountUserChangedJsonModel((AccountUserChanged)domainEvent), AccountUserChanged.NAME));
    }

    private void publish(RemoteEvent remoteEvent) {
        StringEntity eventJson = null;
        try {
            eventJson = new StringEntity(new ObjectMapper().writeValueAsString(remoteEvent));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost("http://localhost:8000/event");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(eventJson);

        httpclient.execute(httpPost, null);
    }
}
