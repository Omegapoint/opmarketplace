package se.omegapoint.academy.opmarketplace.customer.domain;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

import java.io.IOException;
import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Account {
    private Email email;
    private User user;

    private AccountEventPublisher publisher;

    public Account(Email email, User user, AccountEventPublisher publisher) {
        notNull(email);
        notNull(user);
        notNull(publisher);
        this.publisher = publisher;
        this.email = email;
        this.user = user;
        publisher.publishAccountCreated(this);
    }

    // TODO: 3/4/2016 Kan återskapandet av ett account ske här? Känns som infra men jag måste ju ha åtkomst till privata metoder.
    public Account(List<DomainEvent> events, AccountEventPublisher publisher) throws IOException {
        notEmpty(events);
        notNull(publisher);
        this.publisher = publisher;
        for (DomainEvent e: events) {
            if (e instanceof AccountCreated)
                applyCreated((AccountCreated)e);
            else if (e instanceof AccountUserChanged)
                applyUserChanged((AccountUserChanged)e);
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

    public void changeUser(String firstName, String lastName){
        user = user.changeFirstName(firstName);
        user = user.changeLastName(lastName);
        publisher.publishAccountUserChanged(email(), user());
    }

    private void applyCreated(AccountCreated accountCreated){
        this.email = accountCreated.email();
        this.user = accountCreated.user();
    }

    private void applyUserChanged(AccountUserChanged userChanged) {
        this.user = userChanged.user();
    }
}
