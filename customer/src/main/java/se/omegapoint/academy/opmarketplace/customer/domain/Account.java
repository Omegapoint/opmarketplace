package se.omegapoint.academy.opmarketplace.customer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.io.IOException;
import java.util.List;

public class Account {
    private Email email;
    private User user;

    private AccountEventPublisherService publisher;

    public Account(Email email, User user, AccountEventPublisherService publisher) {
        this.publisher = publisher;
        this.email = email;
        this.user = user;
        publisher.publishAccountCreated(this.email, this.user);
    }


    public Account(String email, String firstName, String lastName, AccountEventPublisherService publisher) {
        this.publisher = publisher;
        this.user = new User(firstName, lastName);
        this.email = new Email(email);
        publisher.publishAccountCreated(this.email, this.user);
    }

    public Account(List<DomainEvent> events, AccountEventPublisherService publisher) throws IOException {
        this.publisher = publisher;
        ObjectMapper fromJson = new ObjectMapper();
        for (DomainEvent e: events) {
            if (e.eventType().equals(AccountCreated.class.getSimpleName()))
                applyCreated(fromJson.readValue(e.eventData(), AccountCreated.class));
            else if (e.eventType().equals(AccountUserChanged.class.getSimpleName()))
                applyUserChanged(fromJson.readValue(e.eventData(), AccountUserChanged.class));
        }
    }

    public String id(){
        return email.address();
    }

    public User user(){
        return user;
    }

    public void changeFirstName(String firstName){
        user = new User(firstName, user.lastName());
        publisher.publishAccountChanged(id(), user());
    }

    public void changeLastName(String lastName){
        user = new User(user.firstName(), lastName);
        publisher.publishAccountChanged(id(), user());
    }

    private void applyCreated(AccountCreated accountCreated){
        this.email = new Email(accountCreated.getEmail());
        this.user = new User(accountCreated.getFirstName(), accountCreated.getLastName());
    }

    private void applyUserChanged(AccountUserChanged userChanged){
        this.user = new User(userChanged.getFirstName(), userChanged.getLastName());
    }
}
