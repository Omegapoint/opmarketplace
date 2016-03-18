package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                case AccountCreatedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountCreatedDTO.class));
                    break;
                case AccountNotCreatedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotCreatedDTO.class));
                    break;
                case AccountObtainedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountObtainedDTO.class));
                    break;
                case AccountNotObtainedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotObtainedDTO.class));
                    break;
                case AccountUserChangedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountUserChangedDTO.class));
                    break;
                case AccountUserNotChangedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountUserNotChangedDTO.class));
                    break;
                case AccountDeletedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountDeletedDTO.class));
                    break;
                case AccountNotDeletedDTO.TYPE:
                    router.publish(json.readValue(event.getData(), AccountNotDeletedDTO.class));
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
