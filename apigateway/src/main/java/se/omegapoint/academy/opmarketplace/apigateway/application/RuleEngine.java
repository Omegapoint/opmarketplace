package se.omegapoint.academy.opmarketplace.apigateway.application;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class RuleEngine {

    private ConcurrentHashMap<String, LocalDateTime> prohibitedEvents;

    private HashSet<String> allowedUsers;
    private LocalDateTime filterUsersUntil;

    public RuleEngine() {
        prohibitedEvents = new ConcurrentHashMap<>();
        allowedUsers = new HashSet<>();
        filterUsersUntil = LocalDateTime.MIN;
    }

    public boolean shouldAllowEvent(String eventName) {
        notNull(eventName);
        return !prohibitedEvents.containsKey(eventName) || LocalDateTime.now().isAfter(prohibitedEvents.get(eventName));
    }

    public boolean shouldAllowUser(String email) {
        notNull(email);
        return LocalDateTime.now().isAfter(filterUsersUntil) || allowedUsers.contains(email);
    }

    public void deny(String eventName, int noSeconds) {
        notNull(eventName); notNull(noSeconds);
        prohibitedEvents.put(eventName, LocalDateTime.now().plusSeconds(noSeconds));
        System.out.printf("Disabled %s events until %s%n", eventName, LocalDateTime.now().plusSeconds(noSeconds));
    }
    
    public void allowUsers(List<String> users, int noSeconds) {
        notNull(users); notNull(noSeconds);
        allowedUsers.addAll(users);
        filterUsersUntil = LocalDateTime.now().plusSeconds(noSeconds);
        System.out.printf("Filtering users until %s%n", filterUsersUntil);
    }
}
