package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreationRequested implements DomainEvent{

    private final Email email;
    private final User user;

    public AccountCreationRequested(Email email, User user){
        notNull(email);
        notNull(user);
        this.email = email;
        this.user = user;
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }
}
