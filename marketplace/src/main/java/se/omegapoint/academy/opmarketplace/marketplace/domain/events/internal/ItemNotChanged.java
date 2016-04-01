package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemNotChanged implements DomainEvent {

    private final String reason;

    public ItemNotChanged(String reason) {
        this.reason = notNull(reason);
    }

    public String reason() {
        return reason;
    }
}
