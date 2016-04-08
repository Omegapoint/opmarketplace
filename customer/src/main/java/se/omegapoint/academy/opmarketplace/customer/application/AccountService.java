package se.omegapoint.academy.opmarketplace.customer.application;

import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.services.TransactionService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.*;

import java.util.Optional;
import java.util.UUID;

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
        } else if (dto instanceof AccountCreditDepositRequestedDTO) {
            accountCreditDepositRequested((AccountCreditDepositRequestedDTO) dto);
        } else if (dto instanceof ItemOrderedDTO) {
            accountCreditTransaction((ItemOrderedDTO) dto);
        }
    }

    private void accountCreationRequested(AccountCreationRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(AccountCreationRequestedDTO::domainObject, dto)
                .map(this::createAccount)
                .orElseReason(AccountNotCreated::new);

        publisher.publish(event, dto.requestId());
    }

    private void accountRequested(AccountRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountRequestedDTO::domainObject, dto)
                .map(this::obtainAccount)
                .orElseReason(AccountNotObtained::new);

        publisher.publish(event, dto.requestId());
    }

    private void accountUserChangeRequested(AccountUserChangeRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountUserChangeRequestedDTO::domainObject, dto)
                .map(this::changeUserOfAccount)
                .orElseReason(AccountUserNotChanged::new);

        publisher.publish(event, dto.requestId());
    }

    private void accountDeletionRequested(AccountDeletionRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountDeletionRequestedDTO::domainObject, dto)
                .map(this::deleteAccount)
                .orElseReason(AccountNotDeleted::new);

        publisher.publish(event, dto.requestId());
    }

    private void accountCreditDepositRequested(AccountCreditDepositRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(AccountCreditDepositRequestedDTO::domainObject, dto)
                .map(this::depositCreditsToAccount)
                .orElseReason(AccountCreditNotDeposited::new);

        publisher.publish(event, dto.requestId());
    }

    private void accountCreditTransaction(ItemOrderedDTO dto) {
        DomainEvent event = DomainObjectResult.of(ItemOrderedDTO::domainObject, dto)
                .map(itemOrdered -> TransactionService.creditTransaction(itemOrdered, accountRepository))
                .orElseReason(reason -> new ItemPaymentNotCompleted(UUID.fromString(dto.order.id), reason));

        publisher.publish(event, dto.requestId());
    }


    private DomainEvent createAccount(AccountCreationRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> (DomainEvent) new AccountNotCreated("Account already exists."))
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
                .orElse(new AccountNotObtained("Account does not exist."));
    }

    private DomainEvent changeUserOfAccount(AccountUserChangeRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> {
                    PersistableEvent persistableEvent = account.changeUser(request);
                    accountRepository.append(persistableEvent);
                    return (DomainEvent) persistableEvent;
                })
                .orElse(new AccountUserNotChanged("Account does not exist."));
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

    private DomainEvent depositCreditsToAccount(AccountCreditDepositRequested request) {
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map(account -> {
                    PersistableEvent persistableEvent = account.depositCredits(request);
                    accountRepository.append(persistableEvent);
                    return (DomainEvent) persistableEvent;
                })
                .orElse(new AccountCreditNotDeposited("Account does not exist."));
    }
}
