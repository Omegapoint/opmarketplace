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
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountEventStore implements AccountRepository {

    private AccountCreatedJPA createAccountRepository;
    private AccountUserChangedJPA userChangedRepository;

    public AccountEventStore(AccountCreatedJPA createAccountRepository, AccountUserChangedJPA userChangedRepository){
        this.createAccountRepository = createAccountRepository;
        this.userChangedRepository = userChangedRepository;
    }

    @Override
    public Result<Account> account(Email email){
        List<DomainEvent> domainEvents = new ArrayList<>();
        domainEvents.addAll(createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountCreatedModel::domainEvent)
                .collect(Collectors.toList()));
        if (domainEvents.isEmpty())
            return Result.error("No account for email: " + email.address());
        domainEvents.addAll(userChangedRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList()));
        Collections.sort(domainEvents);
        try {
            return Result.success(new Account(domainEvents));
        }
        catch (IOException e){
            return Result.error("Technical Failure");
        }
        catch (IllegalArgumentValidationException e){
            return Result.error(e.getMessage());
        }
    }

    public Result<Boolean> append(AggregateModification event) {
        if (event instanceof AccountCreated){
            createAccountRepository.save(new AccountCreatedModel((AccountCreated)event));
        }
        else if (event instanceof AccountUserChanged){
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged)event));
        }
        else{
            return Result.success(false);
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> accountInExistence(Email email) {
        return Result.success(!createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).isEmpty());
    }
}
