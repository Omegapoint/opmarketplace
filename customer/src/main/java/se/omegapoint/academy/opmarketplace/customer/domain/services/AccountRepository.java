package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;
import se.omegapoint.academy.opmarketplace.customer.domain.Result;

public interface AccountRepository  {

    Result<Account> account(Email email);

    Result<Boolean> accountInExistence(Email email);

    Result<Boolean> append(AggregateModification event);
}
