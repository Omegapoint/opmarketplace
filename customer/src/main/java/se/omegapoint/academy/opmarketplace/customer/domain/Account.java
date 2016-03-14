package se.omegapoint.academy.opmarketplace.customer.domain;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.io.IOException;
import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;
import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Account {
    private final Email email;
    private final User user;

    public Account(Email email, User user) {
        this.email = notNull(email);
        this.user = notNull(user);
    }

    public String id(){
        return email.address();
    }

    public Email email(){
        return email;
    }

    public User user(){
        return user;
    }

    public static AccountCreated createAccount(AccountRequested request){
        notNull(request);
        return new AccountCreated(request.email(), request.user());
    }

    public AccountUserChanged changeUser(String firstName, String lastName){
        return new AccountUserChanged(this.email(), new User(notBlank(firstName), notBlank(lastName)));
    }
}
