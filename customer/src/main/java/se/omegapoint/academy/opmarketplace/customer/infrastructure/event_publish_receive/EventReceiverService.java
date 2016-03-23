package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountDeletionRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountUserChangeRequestedDTO;

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
                case AccountCreationRequestedDTO.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountCreationRequestedDTO.class)));
                    break;
                case AccountRequestedDTO.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountRequestedDTO.class)));
                    break;
                case AccountUserChangeRequestedDTO.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountUserChangeRequestedDTO.class)));
                    break;
                case AccountDeletionRequestedDTO.TYPE:
                    eventBus.notify(channel, Event.wrap(json.readValue(event.data, AccountDeletionRequestedDTO.class)));
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
