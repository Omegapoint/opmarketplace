package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.events.*;

public interface EventPublisher {
    void publish(AccountCreated event);
    void publish(AccountNotCreated event);
    void publish(AccountObtained event);
    void publish(AccountUserChanged event);
    void publish(AccountNotObtained event);
    void publish(AccountUserNotChanged event);
}
