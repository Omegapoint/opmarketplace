package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.bus.selector.Selectors;
import se.omegapoint.accademy.opmarketplace.messageservice.application.EventDispatcher;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;

import java.util.HashMap;
import java.util.HashSet;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class SubscriptionReceiver {

    @Autowired
    EventBus eventBus;

    @Autowired
    RuleEngine ruleEngine;

    HashMap<String, EventDispatcher> subscribedEndpoints = new HashMap<>();
    HashMap<String, HashSet<String>> subscriptions = new HashMap<>();

    @RequestMapping(value = "/subscribe", method = POST)
    public ResponseEntity<Void> subscribe(
            @RequestParam("channel") String channel,
            @RequestParam("endpoint") String endpoint) {

        subscribeEndpoint(endpoint, Selectors.object(channel));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @RequestMapping(value = "/subscribe_all", method = POST)
    public ResponseEntity<Void> subscribeAll(
            @RequestParam("endpoint") String endpoint,
            @RequestParam("token") String token) {

        if (!token.equals("kebabpizza")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            subscribeEndpoint(endpoint, Selectors.regex("\\w+"));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
    }

    private void subscribeEndpoint(String endpoint, Selector selector) {
        if (!subscribedEndpoints.containsKey(endpoint)) {
            subscribedEndpoints.put(endpoint, new EventDispatcher(ruleEngine, endpoint));
        }

        if (subscriptions.get(endpoint) == null)
            subscriptions.put(endpoint, new HashSet<>());

        // Check if the endpoint is already subscribed to the channel
        if (!subscriptions.get(endpoint).contains(selector.getObject().toString())) {
            subscriptions.get(endpoint).add(selector.getObject().toString());
            eventBus.on(selector, subscribedEndpoints.get(endpoint));
            System.out.printf("Subscribed endpoint %s to channel %s%n", endpoint, selector.getObject().toString());
        }
    }
}
