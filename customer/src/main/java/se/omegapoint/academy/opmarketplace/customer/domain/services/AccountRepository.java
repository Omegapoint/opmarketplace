package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.RepositoryResponse;

public interface AccountRepository  {

    RepositoryResponse<Account> account(Email email);

    RepositoryResponse<Boolean> accountInExistence(Email email);
}
