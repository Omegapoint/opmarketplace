package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompleted implements DomainEvent {
    private final Id orderId;

    public ItemPaymentNotCompleted(Id orderId) {
        this.orderId = notNull(orderId);
    }

    public Id orderId(){
        return orderId;
    }
}
