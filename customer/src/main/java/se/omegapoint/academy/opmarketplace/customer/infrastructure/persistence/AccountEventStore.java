package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
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

public class AccountEventStore implements AccountRepository, Consumer<Event<AggregateModification>> {

    private AccountCreatedJPA createAccountRepository;
    private AccountUserChangedJPA userChangedRepository;
    private AccountEventPublisher accountEventPublisher;

    public AccountEventStore(EventBus eventBus, AccountEventPublisher accountEventPublisher, AccountCreatedJPA createAccountRepository, AccountUserChangedJPA userChangedRepository){
        this.accountEventPublisher = accountEventPublisher;
        this.createAccountRepository = createAccountRepository;
        this.userChangedRepository = userChangedRepository;
        eventBus.on(Selectors.regex("Account\\w*"), this);
    }

    @Override
    public RepositoryResponse<Account> account(Email email){
        List<DomainEvent> domainEvents = new ArrayList<>();
        domainEvents.addAll(createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountCreatedModel::domainEvent)
                .collect(Collectors.toList()));
        if (domainEvents.isEmpty())
            return RepositoryResponse.error("No account for email: " + email.address());
        domainEvents.addAll(userChangedRepository.findByAggregateMemberIdOrderByTime(email.address()).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList()));
        Collections.sort(domainEvents);
        try {
            return RepositoryResponse.success(new Account(domainEvents, accountEventPublisher));
        }
        catch (IOException e){
            return RepositoryResponse.error("Technical Failure");
        }
        catch (IllegalArgumentValidationException e){
            return RepositoryResponse.error(e.getMessage());
        }
    }


    @Override
    public void accept(Event<AggregateModification> event) {
        AggregateModification domainEvent = event.getData();
        if (domainEvent instanceof AccountCreated){
            createAccountRepository.save(new AccountCreatedModel((AccountCreated)domainEvent));
        }
        else if (domainEvent instanceof AccountUserChanged){
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged)domainEvent));
        }
    }

    @Override
    public RepositoryResponse<Boolean> accountInExistence(Email email) {
        return RepositoryResponse.success(!createAccountRepository.findByAggregateMemberIdOrderByTime(email.address()).isEmpty());
    }
}
