package se.omegapoint.academy.opmarketplace.customer.application;

import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.*;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountDeletionRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountRequestedDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountUserChangeRequestedDTO;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;

import static se.omegapoint.academy.opmarketplace.customer.application.Validator.validate;
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

    // TODO: 18/03/16 Method 1, discuss
    private void accountUserChangeRequested(AccountUserChangeRequestedDTO model) {
        try {
            AccountUserChangeRequested request = model.domainObject();
            Optional<Account> maybeAccount = accountRepository.account(request.email());

            DomainEvent event = maybeAccount
                    .map(account -> (DomainEvent) account.changeUser(request))
                    .orElse(new AccountUserNotChanged(request.email().address(), "User does not exist."));

            if (event instanceof PersistableEvent) {
                accountRepository.append((PersistableEvent) event);
            }

            publisher.publish(event, model.requestId());

        } catch (IllegalArgumentValidationException e) {
            AccountUserNotChanged accountUserNotChanged = new AccountUserNotChanged(model.email.address, e.getMessage());
            publisher.publish(accountUserNotChanged, model.requestId());
            e.printStackTrace();
        }
    }

    // TODO: 18/03/16 Method 2, discuss
    private void accountRequested(AccountRequestedDTO model) {
        DomainEvent obtainedEvent = validate(model)
                .map(error -> (DomainEvent) new AccountNotObtained(model.email.address, error))
                .orElseGet(() -> validAccountObtainedEvent(model));

        publisher.publish(obtainedEvent, model.requestId());
    }

    private DomainEvent validAccountObtainedEvent(AccountRequestedDTO model) {
        AccountRequested request = model.domainObject();
        Optional<Account> maybeAccount = accountRepository.account(request.email());

        return maybeAccount
                .map((account) -> (DomainEvent) new AccountObtained(account))
                .orElse(new AccountNotObtained(request.email().address(), "Account does not exist."));
    }

    // TODO: 18/03/16 Method 3, discuss
    private void accountCreationRequested(AccountCreationRequestedDTO model){
        try {
            AccountCreationRequested request = model.domainObject();

            if (!accountRepository.accountInExistence(request.email())){
                AccountCreated accountCreated = Account.createAccount(request);
                accountRepository.append(accountCreated);
                publisher.publish(accountCreated, model.requestId());
            } else {
                AccountNotCreated accountNotCreated = new AccountNotCreated(request.email().address(), "Account already exists.");
                publisher.publish(accountNotCreated, model.requestId());
            }

        } catch (IllegalArgumentValidationException e) {
            e.printStackTrace();
            AccountNotCreated accountNotCreated = new AccountNotCreated(model.email.address, e.getMessage());
            publisher.publish(accountNotCreated, model.requestId());
        }
    }

    private void accountDeletionRequested(AccountDeletionRequestedDTO dto) {
        System.out.println("Handling account deleted: " + dto.email.address);
        try {
            AccountDeletionRequested request = dto.domainObject();

            Optional<Account> maybeAccount = accountRepository.account(request.email());

            if (maybeAccount.isPresent()) {
                Account account = maybeAccount.get();
                AccountDeleted accountDeleted = account.deleteAccount(request);
                accountRepository.append(accountDeleted);
                publisher.publish(accountDeleted, dto.requestId());
            } else {
                System.out.println("Account does not exist.");
                AccountNotDeleted accountNotDeleted = new AccountNotDeleted("Account does not exist.");
                publisher.publish(accountNotDeleted, dto.requestId());
            }
        } catch (IllegalArgumentValidationException e) {
            e.printStackTrace();
            AccountNotDeleted accountNotDeleted = new AccountNotDeleted(e.getMessage());
            publisher.publish(accountNotDeleted, dto.requestId());
        }
    }
}
