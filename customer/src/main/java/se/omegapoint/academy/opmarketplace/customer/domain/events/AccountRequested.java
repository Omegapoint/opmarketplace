package se.omegapoint.academy.opmarketplace.customer.domain.events;


import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequested implements DomainEvent {
    private final Email email;

    public AccountRequested(Email email) {
        this.email = notNull(email);
    }

    public Email email() {
        return email;
    }
}
