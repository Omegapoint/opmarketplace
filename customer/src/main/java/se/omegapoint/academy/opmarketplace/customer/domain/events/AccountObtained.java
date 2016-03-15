package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtained extends DomainEvent {

    private final Account account;
    private final Timestamp timestamp;

    public AccountObtained(Account account, Timestamp timestamp) {
        this.account = notNull(account);
        this.timestamp = notNull(timestamp);
    }

    public Account account() {
        return account;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
