package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountDeletedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountDeletedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories.AccountFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountEventStore implements AccountRepository {

    private final AccountCreatedJPA createAccountRepository;
    private final AccountUserChangedJPA userChangedRepository;
    private final AccountDeletedJPA deleteAccountRepository;

    public AccountEventStore(AccountCreatedJPA createAccountRepository,
                             AccountUserChangedJPA userChangedRepository,
                             AccountDeletedJPA deleteAccountRepository) {
        this.createAccountRepository = notNull(createAccountRepository);
        this.userChangedRepository = notNull(userChangedRepository);
        this.deleteAccountRepository = notNull(deleteAccountRepository);
    }

    @Override
    public Optional<Account> account(Email email) {
        notNull(email);

        Optional<AccountDeleted> maybeAccountDeleted = retrieveDeletedEvent(email);
        if (maybeAccountDeleted.isPresent()) {
            return Optional.empty();
        }

        List<PersistableEvent> events = new ArrayList<>();

        Optional<AccountCreated> maybeAccountCreated = retrieveCreatedEvent(email);
        if (maybeAccountCreated.isPresent()) {
            events.add(maybeAccountCreated.get());
        } else {
            return Optional.empty();
        }

        events.addAll(retrieveUserChangedEvents(email));

        return Optional.of(AccountFactory.fromPersistableEvents(events));
    }

    private Optional<AccountDeleted> retrieveDeletedEvent(Email email) {
        return deleteAccountRepository.findByEmailOrderByTime(email.address()).stream()
                .map(AccountDeletedModel::domainEvent).findAny();
    }

    private Optional<AccountCreated> retrieveCreatedEvent(Email email) {
        return createAccountRepository.findByEmailOrderByTime(email.address()).stream()
                .map(AccountCreatedModel::domainEvent).findAny();
    }

    private List<AccountUserChanged> retrieveUserChangedEvents(Email email) {
        return userChangedRepository.findByEmailOrderByTime(email.address()).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList());
    }

    public void append(PersistableEvent event) {
        notNull(event);
        if (event instanceof AccountCreated) {
            createAccountRepository.save(new AccountCreatedModel((AccountCreated) event));
        } else if (event instanceof AccountUserChanged) {
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged) event));
        } else if (event instanceof AccountDeleted) {
            deleteAccountRepository.save(new AccountDeletedModel((AccountDeleted) event));
        } else {
            throw new IllegalArgumentException("Unsupported persistable event");
        }
    }

    @Override
    public boolean accountInExistence(Email email) {
        notNull(email);
        return !createAccountRepository.findByEmailOrderByTime(email.address()).isEmpty();
    }
}
