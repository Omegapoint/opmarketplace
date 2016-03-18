package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangeRequested {
    private final Email email;
    private final User user;

    public AccountUserChangeRequested(Email email, User user) {
        this.email = notNull(email);
        this.user = notNull(user);
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }
}
