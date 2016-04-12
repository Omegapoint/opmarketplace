package se.omegapoint.academy.opmarketplace.apigateway.application;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class RuleEngine {

    private ConcurrentHashMap<String, LocalDateTime> prohibitedEvents;

    private HashSet<String> allowedUsers;
    private LocalDateTime filterUsersUntil;

    private HashMap<String, LocalDateTime> lastRequests;
    private LocalDateTime rateLimitUsersUntil;
    private long intervalInNano;

    public RuleEngine() {
        prohibitedEvents = new ConcurrentHashMap<>();
        allowedUsers = new HashSet<>();
        filterUsersUntil = LocalDateTime.MIN;
        rateLimitUsersUntil = LocalDateTime.MIN;
        lastRequests = new HashMap<>();
    }

    public boolean shouldAllowEvent(String eventName) {
        notNull(eventName);
        return !prohibitedEvents.containsKey(eventName) || LocalDateTime.now().isAfter(prohibitedEvents.get(eventName));
    }

    public boolean shouldAllowUser(String email) {
        return LocalDateTime.now().isAfter(filterUsersUntil) || email != null && allowedUsers.contains(email);
    }

    public boolean shouldAllowRequestRate(String email, String eventName) {
        boolean allow = false;
        if (email == null) {
            allow = true;
        } else if (LocalDateTime.now().isAfter(rateLimitUsersUntil)) {
            allow = true;
        } else if (lastRequests.getOrDefault(eventName + email, LocalDateTime.MIN).plusNanos(intervalInNano).isBefore(LocalDateTime.now())) {
            allow = true;
        }

        lastRequests.put(eventName + email, LocalDateTime.now());

        return allow;
    }

    public void disableEvent(String eventName, int noSeconds) {
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

    public void addRateLimiting(int interval, int noSeconds) {
        notNull(interval); notNull(noSeconds);
        rateLimitUsersUntil = LocalDateTime.now().plusSeconds(noSeconds);
        this.intervalInNano = TimeUnit.MILLISECONDS.toNanos(interval);
        System.out.printf("Rate limiting users until %s. Min time between requests is %d ms%n", rateLimitUsersUntil, interval);
    }
}
