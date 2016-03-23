package se.omegapoint.academy.opmarketplace.customer.domain.events;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotCreated implements DomainEvent {

    // TODO: 16/03/16 Create value object Reason?
    private final String reason;

    public AccountNotCreated(String reason) {
        this.reason = notNull(reason);
    }

    public String reason() {
        return reason;
    }
}
