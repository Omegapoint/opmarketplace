package se.omegapoint.academy.opmarketplace.customer.domain.events;


import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequested extends DomainEvent {
    private final Email email;
    private final Timestamp timestamp;

    public AccountRequested(Email email, Timestamp timestamp) {
        this.email = notNull(email);
        this.timestamp = notNull(timestamp);
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
