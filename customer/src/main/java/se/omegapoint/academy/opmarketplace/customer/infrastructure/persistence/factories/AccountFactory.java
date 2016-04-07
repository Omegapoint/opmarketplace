package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;

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
            else if (e instanceof AccountCreditDeposited)
                mutate((AccountCreditDeposited)e);
            else if (e instanceof AccountCreditWithdrawn)
                mutate((AccountCreditWithdrawn)e);
        }
        return account;
    }

    private static void mutate(AccountCreated accountCreated) {
        account = new Account(accountCreated.email(), accountCreated.user(), new Credit(0));
    }

    private static void mutate(AccountUserChanged userChanged) {
        account = new Account(account.email(), userChanged.user(), account.vault());
    }

    private static void mutate(AccountCreditDeposited creditDeposited) {
        account = new Account(account.email(), account.user(), account.vault().addCredits(creditDeposited.credit()));
    }

    private static void mutate(AccountCreditWithdrawn creditWithdrawn) {
        account = new Account(account.email(), account.user(), account.vault().removeCredits(creditWithdrawn.credit()));
    }
}
