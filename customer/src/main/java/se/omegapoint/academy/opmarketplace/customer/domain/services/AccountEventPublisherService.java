package se.omegapoint.academy.opmarketplace.customer.domain.services;

import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountUserChanged;

public class AccountEventPublisherService {
    private final EventBus eventBus;

    public AccountEventPublisherService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publishAccountCreated(Email email, User user) {
        eventBus.notify("AccountCreated", Event.wrap(new DomainEvent(email.address(), Account.class, new AccountCreated(email.address(), user.firstName(), user.lastName()))));
    }
    public void publishAccountChanged(String id, User user){
        eventBus.notify("AccountUserChanged", Event.wrap(new DomainEvent(id, Account.class, new AccountUserChanged(user.firstName(), user.lastName()))));
    }
}
