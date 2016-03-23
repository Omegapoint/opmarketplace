package se.omegapoint.academy.opmarketplace.customer.application;

import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountDeletionRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountUserChangeRequestedDTO;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountService implements Consumer<Event<se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event>> {

    private final AccountRepository accountRepository;
    private final EventPublisher publisher;

    public AccountService(AccountRepository accountRepository, EventPublisher publisher) {
        this.accountRepository = accountRepository;
        this.publisher = publisher;
    }

    @Override
    public void accept(Event<se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event> event) {
        notNull(event);

        se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event dto = event.getData();
        if (dto instanceof AccountCreationRequestedDTO) {
            accountCreationRequested((AccountCreationRequestedDTO) dto);
        } else if (dto instanceof AccountRequestedDTO) {
            accountRequested((AccountRequestedDTO) dto);
        } else if (dto instanceof AccountUserChangeRequestedDTO) {
            accountUserChangeRequested((AccountUserChangeRequestedDTO) dto);
        } else if (dto instanceof AccountDeletionRequestedDTO) {
            accountDeletionRequested((AccountDeletionRequestedDTO) dto);
        }
    }

    private void accountCreationRequested(AccountCreationRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(AccountCreationRequestedDTO::domainObject, dto)
                .map(this::createAccount)
                .orElseError(error -> new AccountNotCreated(dto.email.address, error));

        publisher.publish(event, dto.requestId());
    }

    private void accountRequested(AccountRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountRequestedDTO::domainObject, dto)
                .map(this::obtainAccount)
                .orElseError(error -> new AccountNotObtained(dto.email.address, error));

        publisher.publish(event, dto.requestId());
    }

    private void accountUserChangeRequested(AccountUserChangeRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountUserChangeRequestedDTO::domainObject, dto)
                .map(this::changeUserOfAccount)
                .orElseError(error -> new AccountUserNotChanged(dto.email.address, error));

        publisher.publish(event, dto.requestId());
    }

    private void accountDeletionRequested(AccountDeletionRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountDeletionRequestedDTO::domainObject, dto)
                .map(this::deleteAccount)
                .orElseError(AccountNotDeleted::new);

        publisher.publish(event, dto.requestId());
    }

    private DomainEvent createAccount(AccountCreationRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> (DomainEvent) new AccountNotCreated(request.email().address(), "Account already exists."))
                .orElseGet(() -> {
                    PersistableEvent persistableEvent = Account.createAccount(request);
                    accountRepository.append(persistableEvent);
                    return persistableEvent;
                });
    }

    private DomainEvent obtainAccount(AccountRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map((account) -> (DomainEvent) new AccountObtained(account))
                .orElse(new AccountNotObtained(request.email().address(), "Account does not exist."));
    }

    private DomainEvent changeUserOfAccount(AccountUserChangeRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> {
                    PersistableEvent persistableEvent = account.changeUser(request);
                    accountRepository.append(persistableEvent);
                    return (DomainEvent) persistableEvent;
                })
                .orElse(new AccountUserNotChanged(request.email().address(), "Account does not exist."));
    }

    private DomainEvent deleteAccount(AccountDeletionRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> {
                    PersistableEvent persistableEvent = account.deleteAccount(request);
                    accountRepository.append(persistableEvent);
                    return (DomainEvent) persistableEvent;
                })
                .orElse(new AccountNotDeleted("Account does not exist."));
    }
}
