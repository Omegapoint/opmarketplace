package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories.AccountFactory;

import java.util.ArrayList;
import java.util.Collections;
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

        //TODO [dd] consider creating separate method
        domainEvents.addAll(createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountCreatedModel::domainEvent)
                .collect(Collectors.toList()));

        if (domainEvents.isEmpty()) {
            return Optional.empty();
        }

        //TODO [dd] consider creating separate method
        domainEvents.addAll(userChangedRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList()));

        Collections.sort(domainEvents);

        return Optional.of(AccountFactory.fromDomainEvents(domainEvents));
    }

    public void append(AggregateModification event) {
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
        return !createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).isEmpty();
    }
}
