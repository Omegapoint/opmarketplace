package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.*;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;
import java.io.IOException;
import java.util.HashSet;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    private Router router;
    private final HashSet<String> internalEvents;
    private final ObjectMapper json;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> eventInput(@RequestBody JsonNode eventJson) {
        notNull(eventJson);
        IncomingRemoteEvent event = new IncomingRemoteEvent(
                eventJson.get("type").textValue(),
                eventJson.get("data").toString());
        try {
            switch(event.type){
                // Account events
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
                case AccountCreditDepositedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountCreditDepositedDTO.class));
                    break;
                case AccountCreditNotDepositedDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountCreditNotDepositedDTO.class));
                    break;
                case AccountCreditWithdrawnDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountCreditWithdrawnDTO.class));
                    break;
                case AccountCreditNotWithdrawnDTO.TYPE:
                    router.publish(json.readValue(event.data, AccountCreditNotWithdrawnDTO.class));
                    break;
                // Item Events
                case ItemCreatedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemCreatedDTO.class));
                    break;
                case ItemNotCreatedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemNotCreatedDTO.class));
                    break;
                case ItemObtainedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemObtainedDTO.class));
                    break;
                case ItemNotObtainedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemNotObtainedDTO.class));
                    break;
                case ItemSearchCompletedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemSearchCompletedDTO.class));
                    break;
                case ItemsNotSearchedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemsNotSearchedDTO.class));
                    break;
                case ItemChangedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemChangedDTO.class));
                    break;
                case ItemNotChangedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemNotChangedDTO.class));
                    break;
                case ItemPaymentCompletedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemPaymentCompletedDTO.class));
                    break;
                case ItemPaymentNotCompletedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemPaymentNotCompletedDTO.class));
                    break;
                case ItemNotOrderedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemNotOrderedDTO.class));
                    break;
                case ItemReservedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemReservedDTO.class));
                    break;
                case ItemNotReservedDTO.TYPE:
                    router.publish(json.readValue(event.data, ItemNotReservedDTO.class));
                    break;
                default:
                    if (!internalEvents.contains(event.type)) {
                        System.err.println("Received unknown event: " + event.type);
                    }
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IllegalArgumentValidationException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    public EventReceiverService() {
        this.internalEvents = new HashSet<>();
        internalEvents.add(AccountCreationRequestedDTO.TYPE);
        internalEvents.add(AccountUserChangeRequestedDTO.TYPE);
        internalEvents.add(AccountCreditDepositRequestedDTO.TYPE);
        internalEvents.add(AccountCreditWithdrawalRequestedDTO.TYPE);
        internalEvents.add(AccountRequestedDTO.TYPE);
        internalEvents.add(AccountDeletionRequestedDTO.TYPE);
        internalEvents.add(ItemCreationRequestedDTO.TYPE);
        internalEvents.add(ItemRequestedDTO.TYPE);
        internalEvents.add(ItemPurchaseRequestedDTO.TYPE);
        internalEvents.add(ItemSearchRequestedDTO.TYPE);
        internalEvents.add(ItemChangeRequestedDTO.TYPE);
        internalEvents.add(ItemReservationRequestedDTO.TYPE);
        this.json = new ObjectMapper();
    }
}
