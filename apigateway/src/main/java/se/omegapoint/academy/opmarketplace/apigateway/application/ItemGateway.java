package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.Router;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item.ItemCreatedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item.ItemObtainedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.OutgoingRemoteEvent;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemRequestedDTO;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/items")
public class ItemGateway {

    private final long TIMEOUT = 2000;
    private final ResponseEntity<String> TIMEOUT_RESPONSE = ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("");

    @Autowired
    private Router router;

    @Autowired
    private RemoteEventPublisher publisher;

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> createAccount(@RequestBody final ItemCreationRequestedDTO newItem) {
        notNull(newItem);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        ItemCreatedListener listener =  new ItemCreatedListener(result);
        router.subscribe(newItem.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(newItem), "Item");
        return result;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> account(@RequestParam("itemId") final String itemId) {
        ItemRequestedDTO itemRequested = new ItemRequestedDTO(notNull(itemId));
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        ItemObtainedListener listener =  new ItemObtainedListener(result);
        router.subscribe(itemRequested.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(itemRequested), "Item");
        return result;
    }

}
