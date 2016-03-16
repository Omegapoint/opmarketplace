package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserNotChanged extends DomainEvent {

    private final String email;
    private final String reason;

    public AccountUserNotChanged(String email, String reason) {
        this.email = notNull(email);
        this.reason = notNull(reason);
    }

    public String email() {
        return email;
    }

    public String reason() {
        return reason;
    }

    @Override
    public Timestamp timestamp() {
        return null;
    }
}
