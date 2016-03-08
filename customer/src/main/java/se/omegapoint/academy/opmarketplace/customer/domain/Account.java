package se.omegapoint.academy.opmarketplace.customer.domain;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

import java.io.IOException;
import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Account {
    private Email email;
    private User user;

    public Account(List<DomainEvent> events) throws IOException {
        notEmpty(events);
        for (DomainEvent e: events) {
            notNull(e);
            if (e instanceof AccountCreated)
                mutate((AccountCreated)e);
            else if (e instanceof AccountUserChanged)
                mutate((AccountUserChanged)e);
        }
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

    public AccountUserChanged changeUser(String firstName, String lastName){
        AccountUserChanged accountUserChanged = new AccountUserChanged(this.email(), new User(firstName, lastName));
        mutate(accountUserChanged);
        return accountUserChanged;
    }

    private void mutate(AccountCreated accountCreated){
        this.email = accountCreated.email();
        this.user = accountCreated.user();
    }

    private void mutate(AccountUserChanged userChanged) {
        this.user = userChanged.user();
    }

    public static AccountCreated requestAccount(AccountRequested request){
        return new AccountCreated(request.email(), request.user());
    }
}
