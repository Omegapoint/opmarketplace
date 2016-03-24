package se.omegapoint.accademy.opmarketplace.messageservice.infrastructure;

import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import se.omegapoint.accademy.opmarketplace.messageservice.application.EventDispatcher;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class SubscriptionController {
    private final EventBus eventBus;
    private final RuleEngine ruleEngine;

    private HashMap<URL, EventDispatcher> subscribedEndpoints = new HashMap<>();
    private HashMap<URL, HashSet<String>> subscriptions = new HashMap<>();

    public SubscriptionController(EventBus eventBus, RuleEngine ruleEngine) {
        this.eventBus = eventBus;
        this.ruleEngine = ruleEngine;
    }

    public boolean subscribeEndpoint(URL endpoint, Selector selector) {
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
            return true;
        } else {
            System.out.printf("Endpoint %s is already subscribed to channel %s%n", endpoint, selector.getObject().toString());
            for (URL url : subscriptions.keySet()) {
                System.out.printf("    URL %s subscribed to %s%n", url, Arrays.toString(subscriptions.get(url).toArray()));
            }
            return false;
        }
    }
}
