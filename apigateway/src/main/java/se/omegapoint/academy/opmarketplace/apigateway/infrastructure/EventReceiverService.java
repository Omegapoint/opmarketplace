package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;
import java.io.IOException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {


    private final ObjectMapper json;
    private final Router router;

    public EventReceiverService(Router router) {
        this.router = notNull(router);
        this.json = new ObjectMapper();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestBody JsonNode eventJson) {
        notNull(eventJson);
        IncomingRemoteEvent event = new IncomingRemoteEvent(
                eventJson.get("type").textValue(),
                eventJson.get("data").toString());
        try {
            switch(event.type){
                case AccountCreatedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountCreatedDTO.class));
                    break;
                case AccountNotCreatedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountNotCreatedDTO.class));
                    break;
                case AccountObtainedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountObtainedDTO.class));
                    break;
                case AccountNotObtainedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountNotObtainedDTO.class));
                    break;
                case AccountUserChangedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountUserChangedDTO.class));
                    break;
                case AccountUserNotChangedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountUserNotChangedDTO.class));
                    break;
                case AccountDeletedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountDeletedDTO.class));
                    break;
                case AccountNotDeletedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountNotDeletedDTO.class));
                    break;
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentValidationException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
