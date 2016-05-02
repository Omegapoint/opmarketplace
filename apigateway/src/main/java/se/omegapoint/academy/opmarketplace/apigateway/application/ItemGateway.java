package se.omegapoint.academy.opmarketplace.apigateway.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.Router;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.OutgoingRemoteEvent;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/items")
public class ItemGateway {

    @Value("${event.timeout}")
    private long TIMEOUT;
    private final ResponseEntity<String> TIMEOUT_RESPONSE = ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("");

    @Autowired
    private Router router;

    @Autowired
    private RuleEngine ruleEngine;

    @Autowired
    private RemoteEventPublisher publisher;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> createItem(@RequestBody final ItemCreationRequestedDTO newItem) {
        notNull(newItem);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        ItemCreatedListener listener =  new ItemCreatedListener(result);
        router.subscribe(newItem.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(newItem), "Item");
        return result;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> item(
            @RequestParam("id") final ItemRequestedDTO request,
            @RequestParam(value = "email", required = false) String email) {
        notNull(request);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);

        // TODO: 02/05/16 Workaround due to Loader.io
        if (email != null && email.contains(",")) {
            email = email.split(",")[0];
        }

        if (!ruleEngine.shouldAllowUser(email) || !ruleEngine.shouldAllowRequestRate(email, ItemRequestedDTO.TYPE)) {
            result.setResult(new ResponseEntity<>(HttpStatus.FORBIDDEN));
            return result;
        }

        ItemObtainedListener listener =  new ItemObtainedListener(result);
        router.subscribe(request.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(request), "Item");
        return result;
    }

    @RequestMapping(value = "/search", method = GET, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> searchItems(@RequestParam("query") final ItemSearchRequestedDTO request) throws JsonProcessingException {
        notNull(request);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);

        Optional<ItemDTO> maybeItem = ruleEngine.getDefaultSearchResult();
        if (maybeItem.isPresent()) {
            ResponseEntity<String> responseEntity = ResponseEntity.ok(objectMapper.writeValueAsString(Collections.singletonList(maybeItem.get())));
            result.setResult(responseEntity);
            return result;
        }

        ItemsSearchedListener listener =  new ItemsSearchedListener(result);
        router.subscribe(request.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(request), "Item");
        return result;
    }

    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> changeItem(@RequestBody final ItemChangeRequestedDTO change) {
        notNull(change);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        ItemChangedListener listener =  new ItemChangedListener(result);
        router.subscribe(change.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(change), "Item");
        return result;
    }

    @RequestMapping(value = "/purchase", method = POST, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> createItem(@RequestBody final ItemPurchaseRequestedDTO newItem) {
        notNull(newItem);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        ItemPurchaseListener listener =  new ItemPurchaseListener(result);
        router.subscribe(newItem.requestId(), listener);
        publisher.publish(new OutgoingRemoteEvent(newItem), "Item");
        return result;
    }

}
