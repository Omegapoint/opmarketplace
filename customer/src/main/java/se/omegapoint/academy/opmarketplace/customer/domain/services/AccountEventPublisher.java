package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

public interface AccountEventPublisher {
    void publishAccountCreated(Account account);

    void publishAccountUserChanged(Email id, User user);
}
