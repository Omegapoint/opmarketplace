package se.omegapoint.academy.opmarketplace.customer.application;

import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.*;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.AccountUserChangeRequestedModel;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.sql.Timestamp;
import java.util.Optional;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountService implements Consumer<Event<DTO>> {

    private final AccountRepository accountRepository;
    private final EventPublisher publisher;

    public AccountService(AccountRepository accountRepository, EventPublisher publisher) {
        this.accountRepository = accountRepository;
        this.publisher = publisher;
    }

    // TODO: 17/03/16 Discuss exception usage?
    // TODO: 17/03/16 Fail events are sent in two places.
    @Override
    public void accept(Event<DTO> event) {
        notNull(event);

        DTO DTO = event.getData();
        if (DTO instanceof AccountCreationRequestedModel) {
            accountCreationRequested((AccountCreationRequestedModel) DTO);
        } else if (DTO instanceof AccountRequestedModel) {
            accountRequested((AccountRequestedModel) DTO);
        } else if (DTO instanceof AccountUserChangeRequestedModel) {
            accountUserChangeRequested((AccountUserChangeRequestedModel) DTO);
        }
    }

    private void accountUserChangeRequested(AccountUserChangeRequestedModel model) {
        try {
            AccountUserChangeRequested request = model.domainObject();
            Optional<Account> maybeAccount = accountRepository.account(request.email());

            if (maybeAccount.isPresent()) {
                Account account = maybeAccount.get();
                AccountUserChanged userChangedEvent = account.changeUser(request);
                accountRepository.append(userChangedEvent);
                publisher.publish(userChangedEvent);
            } else {
                AccountUserNotChanged accountUserNotChanged = new AccountUserNotChanged(request.email().address(), "User does not exist.");
                publisher.publish(accountUserNotChanged);
            }
        } catch (IllegalArgumentValidationException e) {
            AccountUserNotChanged accountUserNotChanged = new AccountUserNotChanged(model.getEmail().getAddress(), e.getMessage());
            publisher.publish(accountUserNotChanged);
            e.printStackTrace();
        }
    }

    private void accountRequested(AccountRequestedModel model) {
        try {
            AccountRequested request = model.domainObject();
            Optional<Account> maybeAccount = accountRepository.account(request.email());

            if (maybeAccount.isPresent()) {
                AccountObtained accountObtained = new AccountObtained(maybeAccount.get());
                publisher.publish(accountObtained);
            } else {
                AccountNotObtained accountNotObtained = new AccountNotObtained(request.email().address(), "Account does not exist.");
                publisher.publish(accountNotObtained);
            }

        } catch (IllegalArgumentValidationException e) {
            e.printStackTrace();
            AccountNotObtained accountNotObtained = new AccountNotObtained(model.getEmail().getAddress(), e.getMessage());
            publisher.publish(accountNotObtained);
        }
    }

    private void accountCreationRequested(AccountCreationRequestedModel model){
        try {
            AccountCreationRequested request = model.domainObject();

            if (!accountRepository.accountInExistence(request.email())){
                AccountCreated accountCreated = Account.createAccount(request);
                accountRepository.append(accountCreated);
                publisher.publish(accountCreated);
            } else {
                AccountNotCreated accountNotCreated = new AccountNotCreated(request.email().address(), "Account already exists.");
                publisher.publish(accountNotCreated);
            }

        } catch (IllegalArgumentValidationException e) {
            e.printStackTrace();
            AccountNotCreated accountNotCreated = new AccountNotCreated(model.getEmail().getAddress(), e.getMessage());
            publisher.publish(accountNotCreated);
        }
    }
}
