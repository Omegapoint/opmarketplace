package se.omegapoint.academy.opmarketplace.customer.domain.events;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompleted implements DomainEvent  {
    private final String reason;

    public ItemPaymentNotCompleted(String reason) {
        this.reason = notNull(reason);
    }

    public String reason() {
        return reason;
    }
}
