package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountObtainedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.RemoteEvent;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    private EventBus eventBus;
    private ObjectMapper json = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestBody RemoteEvent event) {
        notNull(event);
        try {
            switch(event.getType()){
                case AccountCreatedModel.TYPE:
                    AccountCreatedModel accountCreatedModel = json.readValue(event.getData(), AccountCreatedModel.class);
                    eventBus.notify(AccountCreatedModel.TYPE + accountCreatedModel.getEmail().getAddress(), Event.wrap(accountCreatedModel));
                    break;
                case AccountObtainedModel.TYPE:
                    AccountObtainedModel accountObtainedModel = json.readValue(event.getData(), AccountObtainedModel.class);
                    eventBus.notify(AccountObtainedModel.TYPE + accountObtainedModel.getAccount().getEmail().getAddress(), Event.wrap(accountObtainedModel));
                    break;
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentException | IllegalArgumentValidationException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
