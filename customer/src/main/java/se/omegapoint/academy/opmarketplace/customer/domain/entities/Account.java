package se.omegapoint.academy.opmarketplace.customer.domain.entities;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChangeRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

import static se.sawano.java.commons.lang.validate.Validate.*;

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

    public static AccountCreated createAccount(AccountCreationRequested request){
        notNull(request);
        return new AccountCreated(request.email(), request.user());
    }

    public AccountUserChanged changeUser(AccountUserChangeRequested request){
        notNull(request);
        isTrue(this.email.equals(request.email()));
        return new AccountUserChanged(this.email(), request.user());
    }
}
