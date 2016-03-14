package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountFactory {
    private static Account account;

    private AccountFactory() {}

    public static Account fromDomainEvents(List<DomainEvent> events) {
        notEmpty(events);
        for (DomainEvent e: events) {
            notNull(e);
            if (e instanceof AccountCreated)
                mutate((AccountCreated)e);
            else if (e instanceof AccountUserChanged)
                mutate((AccountUserChanged)e);
        }
        return account;
    }

    private static void mutate(AccountCreated accountCreated) {
        account = new Account(accountCreated.email(), accountCreated.user());
    }

    private static void mutate(AccountUserChanged userChanged) {
        account = new Account(account.email(), userChanged.user());
    }
}
