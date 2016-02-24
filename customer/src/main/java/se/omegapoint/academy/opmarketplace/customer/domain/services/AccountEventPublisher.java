package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

public interface AccountEventPublisher {
    void publishAccountCreated(Email email, User user);

    void publishAccountUserChanged(String id, User user);
}
