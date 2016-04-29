package se.omegapoint.academy.opmarketplace.apigateway.application;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class RuleEngine {

    private ConcurrentHashMap<String, LocalDateTime> prohibitedEvents;

    private HashSet<String> allowedUsers;
    private LocalDateTime filterUsersUntil;
    private boolean onlyImportantUsers;

    private HashMap<String, LocalDateTime> lastRequests;
    private LocalDateTime rateLimitUsersUntil;
    private long intervalInNano;

    private LocalDateTime defaultSearchResultUntil;
    private ItemDTO defaultSearchResult;

    public RuleEngine() {
        prohibitedEvents = new ConcurrentHashMap<>();
        allowedUsers = new HashSet<>();
        filterUsersUntil = LocalDateTime.MIN;
        rateLimitUsersUntil = LocalDateTime.MIN;
        defaultSearchResultUntil = LocalDateTime.MIN;
        lastRequests = new HashMap<>();
    }

    public boolean shouldAllowEvent(String eventName) {
        notNull(eventName);
        return !prohibitedEvents.containsKey(eventName) || LocalDateTime.now().isAfter(prohibitedEvents.get(eventName));
    }

    public boolean shouldAllowUser(String email) {
        if (onlyImportantUsers) {
            return LocalDateTime.now().isAfter(filterUsersUntil) || email != null && allowedUsers.contains(email);
        } else {
            return LocalDateTime.now().isAfter(filterUsersUntil) || email != null;
        }
    }

    public Optional<ItemDTO> getDefaultSearchResult() {
        if (LocalDateTime.now().isAfter(defaultSearchResultUntil)) {
            return Optional.empty();
        } else {
            return Optional.of(defaultSearchResult);
        }
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
    
    public void allowUsers(List<String> users, int noSeconds, boolean onlyImportantUsers) {
        notNull(users); notNull(noSeconds); notNull(onlyImportantUsers);
        allowedUsers.addAll(users);
        filterUsersUntil = LocalDateTime.now().plusSeconds(noSeconds);
        this.onlyImportantUsers = onlyImportantUsers;
        System.out.printf("Filtering users until %s%n", filterUsersUntil);
    }

    public void addRateLimiting(int interval, int noSeconds) {
        notNull(interval); notNull(noSeconds);
        rateLimitUsersUntil = LocalDateTime.now().plusSeconds(noSeconds);
        this.intervalInNano = TimeUnit.MILLISECONDS.toNanos(interval);
        System.out.printf("Rate limiting users until %s. Min time between requests is %d ms%n", rateLimitUsersUntil, interval);
    }

    public void setDefaultSearchResult(ItemDTO item, int noSeconds) {
        this.defaultSearchResult = item;
        this.defaultSearchResultUntil = LocalDateTime.now().plusSeconds(noSeconds);
    }
}
