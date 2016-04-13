package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Id;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompleted implements DomainEvent  {
    private final Id orderId;
    private final String reason;

    public ItemPaymentNotCompleted(Id orderId, String reason) {
        this.orderId = orderId;
        this.reason = notNull(reason);
    }

    public Id orderId(){
        return orderId;
    }

    public String reason() {
        return reason;
    }
}
