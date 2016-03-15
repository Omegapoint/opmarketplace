package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.RemoteEvent;

import java.io.IOException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    private EventBus eventBus;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestParam("channel") final String channel, @RequestBody RemoteEvent event) {
        notNull(channel);
        notNull(event);

        try {
            switch (event.getType()) {
                case AccountCreationRequestedModel.TYPE:
                    AccountCreationRequestedModel accountCreationRequestedModel = objectMapper.readValue(event.getData(), AccountCreationRequestedModel.class);
                    eventBus.notify(channel, Event.wrap(accountCreationRequestedModel));
                    break;
                case AccountRequestedModel.TYPE:
                    AccountRequestedModel accountRequestedModel = objectMapper.readValue(event.getData(), AccountRequestedModel.class);
                    eventBus.notify(channel, Event.wrap(accountRequestedModel));
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
