package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.RemoteEvent;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    EventBus eventBus;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestParam("channel") final String channel, @RequestBody RemoteEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (event.getType().equals(se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated.NAME))
                eventBus.notify(Selectors.object(channel), Event.wrap(objectMapper.readValue(event.getData(), AccountCreatedModel.class).domainObject()));
            if (event.getType().equals(se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged.NAME))
                eventBus.notify(Selectors.object(channel), Event.wrap(objectMapper.readValue(event.getData(), AccountUserChangedModel.class).domainObject()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentException | IllegalArgumentValidationException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
