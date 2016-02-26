package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing;

import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

public class AccountEventPublisherService implements AccountEventPublisher {

    private final EventBus eventBus;

    public AccountEventPublisherService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publishAccountCreated(Account account) {
        eventBus.notify(AccountCreated.CHANNEL, Event.wrap(new AccountCreated(account.email(), account.user())));
    }

    @Override
    public void publishAccountUserChanged(Email id, User user){
        eventBus.notify(AccountUserChanged.CHANNEL, Event.wrap(new AccountUserChanged(id, user)));
    }
}
