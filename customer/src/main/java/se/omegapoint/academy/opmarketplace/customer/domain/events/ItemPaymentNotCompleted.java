package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompleted implements DomainEvent  {
    private final UUID orderId;
    private final String reason;

    public ItemPaymentNotCompleted(UUID orderId, String reason) {
        this.orderId = orderId;
        this.reason = notNull(reason);
    }

    public UUID orderId(){
        return orderId;
    }

    public String reason() {
        return reason;
    }
}
