package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionRequested implements DomainEvent {

    private final Email email;

    public AccountDeletionRequested(Email email) {
        this.email = notNull(email);
    }

    public Email email() {
        return email;
    }
}
