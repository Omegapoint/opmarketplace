package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemNotCreated implements DomainEvent{

    private final String reason;

    public ItemNotCreated(String reason) {
        this.reason = notNull(reason);
    }

    public String reason() {
        return reason;
    }
}
