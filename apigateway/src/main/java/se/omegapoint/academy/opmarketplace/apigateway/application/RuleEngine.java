package se.omegapoint.academy.opmarketplace.apigateway.application;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

public class RuleEngine {

    private ConcurrentHashMap<String, LocalTime> prohibitedEvents;

    public RuleEngine() {
        prohibitedEvents = new ConcurrentHashMap<>();
    }

    public boolean allow(String eventName) {
        return !prohibitedEvents.containsKey(eventName) || LocalTime.now().isAfter(prohibitedEvents.get(eventName));
    }

    public void deny(String eventName, int noSeconds) {
        prohibitedEvents.put(eventName, LocalTime.now().plusSeconds(noSeconds));
        System.out.printf("Disabled %s events until %s%n", eventName, LocalTime.now().plusSeconds(noSeconds));
    }
}
