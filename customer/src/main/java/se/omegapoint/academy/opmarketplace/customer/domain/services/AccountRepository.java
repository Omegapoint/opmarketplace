package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;

import java.io.IOException;

public interface AccountRepository  {

    Account account(String email) throws IOException;

    boolean accountInExistence(String email);
}
