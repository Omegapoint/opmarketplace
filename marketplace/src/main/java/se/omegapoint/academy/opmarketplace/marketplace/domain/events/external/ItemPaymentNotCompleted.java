package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompleted implements DomainEvent {
    private final UUID orderId;

    public ItemPaymentNotCompleted(UUID orderId) {
        this.orderId = notNull(orderId);
    }

    public UUID orderId(){
        return orderId;
    }
}
