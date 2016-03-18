package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    private Router router;
    private ObjectMapper json = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestBody RemoteEvent event) {
        notNull(event);
        try {
            switch(event.getType()){
                case AccountCreatedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountCreatedModel.class));
                    break;
                case AccountNotCreatedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotCreatedModel.class));
                    break;
                case AccountObtainedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountObtainedModel.class));
                    break;
                case AccountNotObtainedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotObtainedModel.class));
                    break;
                case AccountUserChangedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountUserChangedModel.class));
                    break;
                case AccountUserNotChangedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountUserNotChangedModel.class));
                    break;
                case AccountDeletedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountDeletedModel.class));
                    break;
                case AccountNotDeletedModel.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotDeletedModel.class));
                    break;
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
