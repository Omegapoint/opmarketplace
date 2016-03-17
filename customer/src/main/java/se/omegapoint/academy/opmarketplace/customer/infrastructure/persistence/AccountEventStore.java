package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
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

    public AccountEventStore(AccountCreatedJPA createAccountRepository, AccountUserChangedJPA userChangedRepository) {
        this.createAccountRepository = notNull(createAccountRepository);
        this.userChangedRepository = notNull(userChangedRepository);
    }

    @Override
    public Optional<Account> account(Email email) {

        notNull(email);

        List<DomainEvent> domainEvents = new ArrayList<>();

        Optional<AccountCreated> maybeAccountCreated = retrieveCreatedEvent(email);
        if (maybeAccountCreated.isPresent()) {
            domainEvents.add(maybeAccountCreated.get());
        } else {
            return Optional.empty();
        }

        domainEvents.addAll(retrieveUserChangedEvents(email));

        return Optional.of(AccountFactory.fromDomainEvents(domainEvents));
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

    // TODO: 16/03/16 Maybe ModifyingEvent interface?
    public void append(DomainEvent event) {
        notNull(event);
        if (event instanceof AccountCreated) {
            createAccountRepository.save(new AccountCreatedModel((AccountCreated) event));
        } else if (event instanceof AccountUserChanged) {
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged) event));
        }
    }

    @Override
    public boolean accountInExistence(Email email) {
        notNull(email);
        return !createAccountRepository.findByEmailOrderByTime(email.address()).isEmpty();
    }
}
