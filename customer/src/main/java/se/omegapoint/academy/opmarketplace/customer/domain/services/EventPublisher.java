package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountObtained;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

public interface EventPublisher {
    void publish(AccountCreated event);
    void publish(AccountNotCreated event);
    void publish(AccountObtained event);
    void publish(AccountUserChanged event);
}
