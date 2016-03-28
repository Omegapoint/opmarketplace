package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemRequested implements DomainEvent {

    private final UUID itemId;

    public ItemRequested(UUID itemId) {
        this.itemId = notNull(itemId);
    }

    public UUID itemID(){
        return this.itemId;
    }
}
