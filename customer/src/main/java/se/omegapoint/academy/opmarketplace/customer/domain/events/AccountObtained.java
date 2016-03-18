package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtained implements DomainEvent {

    private final Account account;

    public AccountObtained(Account account) {
        this.account = notNull(account);
    }

    public Account account() {
        return account;
    }
}
