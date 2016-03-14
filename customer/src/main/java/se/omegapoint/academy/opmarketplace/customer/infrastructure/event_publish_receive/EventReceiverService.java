package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
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
        //TODO [dd] add notNull contracts

        ObjectMapper objectMapper = new ObjectMapper(); //TODO [dd]: Consider making a final field. A lot is done during creation!

        try {
            if (event.getType().equals(AccountCreationRequested.NAME)) {
                AccountCreationRequested accountCreationRequested = objectMapper.readValue(event.getData(), AccountCreationRequestedModel.class).domainObject().value();
                eventBus.notify(channel, Event.wrap(accountCreationRequested));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentException | IllegalArgumentValidationException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
