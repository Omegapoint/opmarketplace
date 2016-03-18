package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountUserChangeRequestedModel;

import java.io.IOException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    private EventBus eventBus;

    private final ObjectMapper json = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestParam("channel") final String channel, @RequestBody JsonNode eventJson) {
        notNull(channel);
        notNull(eventJson);
        IncomingRemoteEvent event = new IncomingRemoteEvent(
                eventJson.get("type").textValue(),
                eventJson.get("data").toString());
        try {
            switch (event.type) {
                case AccountCreationRequestedModel.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountCreationRequestedModel.class)));
                    break;
                case AccountRequestedModel.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountRequestedModel.class)));
                    break;
                case AccountUserChangeRequestedModel.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountUserChangeRequestedModel.class)));
                    break;
                default:
                    System.err.printf("Received unknown event; %s%n", event.type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
