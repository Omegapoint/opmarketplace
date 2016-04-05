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

        if (!subscriptions.containsKey(endpoint)) {
            subscriptions.put(endpoint, new HashSet<>());
        }

        // Check if the endpoint is already subscribed to the channel
        String channel = selector.getObject().toString();
        System.out.printf("DEBUG: Hashcode = %d for endpoint %s and channel %s %n", endpoint.hashCode(), endpoint, channel);
        if (!subscriptions.get(endpoint).contains(channel)) {
            subscriptions.get(endpoint).add(channel);
            eventBus.on(selector, subscribedEndpoints.get(endpoint));
            System.out.printf("DEBUG: Subscribed endpoint %s to channel %s%n", endpoint, channel);
            return true;
        } else {
            System.out.printf("DEBUG: Endpoint %s is already subscribed to channel %s%n", endpoint, channel);
            for (URL url : subscriptions.keySet()) {
                System.out.printf("    DEBUG: URL %s subscribed to %s%n", url, Arrays.toString(subscriptions.get(url).toArray()));
            }
            return false;
        }
    }

    public HashMap subscriptions() {
        return subscriptions;
    }
}
