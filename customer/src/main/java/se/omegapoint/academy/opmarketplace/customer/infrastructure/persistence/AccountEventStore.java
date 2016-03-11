package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountEventStore implements AccountRepository {

    //TODO [dd]: make immutable

    private AccountCreatedJPA createAccountRepository;
    private AccountUserChangedJPA userChangedRepository;

    public AccountEventStore(AccountCreatedJPA createAccountRepository, AccountUserChangedJPA userChangedRepository) {
        this.createAccountRepository = createAccountRepository;
        this.userChangedRepository = userChangedRepository;
    }

    @Override
    public Result<Account> account(Email email) {
        //TODO [dd] add notNull contracts

        List<DomainEvent> domainEvents = new ArrayList<>();

        //TODO [dd] consider creating separate method
        domainEvents.addAll(createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountCreatedModel::domainEvent)
                .collect(Collectors.toList()));

        //TODO [dd] is this an error, success, or bug? If the latter, change to contract
        if (domainEvents.isEmpty())
            return Result.error("No account for email: " + email.address());

        //TODO [dd] consider creating separate method
        domainEvents.addAll(userChangedRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList()));

        Collections.sort(domainEvents);

        try {
            return Result.success(new Account(domainEvents));
        } catch (IOException e) {
            return Result.error("Technical Failure");
        } catch (IllegalArgumentValidationException e) {
            return Result.error(e.getMessage()); //TODO [dd] is this really an error?
        }
    }

    public Result<Boolean> append(AggregateModification event) {
        //TODO [dd] add notNull contracts
        if (event instanceof AccountCreated) {
            createAccountRepository.save(new AccountCreatedModel((AccountCreated) event));
        } else if (event instanceof AccountUserChanged) {
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged) event));
        } else {
            return Result.success(false);
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> accountInExistence(Email email) {
        //TODO [dd] add notNull contracts
        return Result.success(!createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).isEmpty());
    }
}
