package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountFactory {
    private static Account account;

    private AccountFactory() {}

    public static Account fromPersistableEvents(List<PersistableEvent> events) {
        notEmpty(events);
        for (PersistableEvent e: events) {
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
