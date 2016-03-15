package se.omegapoint.academy.opmarketplace.customer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.*;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.sql.Timestamp;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/accounts")
public class AccountService implements Consumer<Event<JsonModel>> {

    @Autowired
    private EventPublisher publisher;

    @Autowired
    private AccountRepository accountRepository;

    //TODO [dd]: is this method really idempotent? PUT operations must be idempotent according to the specification. See https://spring.io/understanding/REST
    @RequestMapping(method = PUT)
    public ResponseEntity changeUser(@RequestParam("email") final Email email, @RequestBody UserModel userModel) {
        //TODO [dd] add notNull contracts

        Optional<Account> maybeAccount = accountRepository.account(email);
        if (maybeAccount.isPresent()){
            Account account = maybeAccount.get();
            //TODO [dd]: consider moving into domain
            AccountUserChanged accountUserChanged = account.changeUser(userModel.getFirstName(), userModel.getLastName());
            accountRepository.append(accountUserChanged);
            publisher.publish(accountUserChanged);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @Override
    public void accept(Event<JsonModel> event) {
        notNull(event);

        JsonModel jsonModel = event.getData();
        if (jsonModel instanceof AccountCreationRequestedModel) {
            accountCreationRequested((AccountCreationRequestedModel) jsonModel);
        } else if (jsonModel instanceof AccountRequestedModel) {
            accountRequested((AccountRequestedModel) jsonModel);
        } else if (jsonModel instanceof  AccountUserChangeRequestedModel) {
            accountUserChangeRequested((AccountUserChangeRequestedModel) jsonModel);
        }
    }

    private void accountUserChangeRequested(AccountUserChangeRequestedModel model) {
        try {
            AccountUserChangeRequested request = model.domainObject();
            Optional<Account> maybeAccount = accountRepository.account(request.email());

            if (maybeAccount.isPresent()) {
                Account account = maybeAccount.get();
                AccountUserChanged userChangedEvent = account.changeUser(request.user().firstName(), request.user().lastName());
                accountRepository.append(userChangedEvent);
                publisher.publish(userChangedEvent);
            } else {
                // TODO: 15/03/16 Send account user not changed with reason
            }
        } catch (IllegalArgumentValidationException e) {
            // TODO: 15/03/16 Send account user not changed with reason
            e.printStackTrace();
        }
    }

    private void accountRequested(AccountRequestedModel accountRequestedModel) {
        try {
            AccountRequested request = accountRequestedModel.domainObject();
            Optional<Account> maybeAccount = accountRepository.account(request.email());

            if (maybeAccount.isPresent()) {
                AccountObtained accountObtained = new AccountObtained(maybeAccount.get(), new Timestamp(System.currentTimeMillis()));
                publisher.publish(accountObtained);
            } else {
                // TODO: 15/03/16 Send account not obtained event with reason
            }

        } catch (IllegalArgumentValidationException e) {
            // TODO: 15/03/16 Send account not obtained event with reason
            e.printStackTrace();
        }
    }

    private void accountCreationRequested(AccountCreationRequestedModel accountCreationRequestedModel){
        try {
            AccountCreationRequested request = accountCreationRequestedModel.domainObject();

            if (!accountRepository.accountInExistence(request.email())){
                AccountCreated accountCreated = Account.createAccount(request);
                accountRepository.append(accountCreated);
                publisher.publish(accountCreated);
            } else {
                // TODO: 15/03/16 Send user not created event with reason
            }

        } catch (IllegalArgumentValidationException e) {
            // TODO: 15/03/16 Send user not created event with reason
            e.printStackTrace();
        }
    }
}
