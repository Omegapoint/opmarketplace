package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.util.Optional;

public interface AccountRepository  {

    Optional<Account> account(Email email);

    boolean accountInExistence(Email email);

    void append(DomainEvent event);
}
