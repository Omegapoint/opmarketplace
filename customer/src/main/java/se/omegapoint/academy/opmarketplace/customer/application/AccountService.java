package se.omegapoint.academy.opmarketplace.customer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/accounts")
public class AccountService implements Consumer<Event<DomainEvent>> {

    @Autowired
    private EventPublisher publisher;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = POST)
    public ResponseEntity createAccount(@RequestBody final AccountCreationRequestedModel newAccount) {
        //TODO [dd] add notNull contracts

        AccountCreationRequested accountCreationRequested = null;
        try {
            accountCreationRequested = newAccount.domainObject();
        } catch (IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        //TODO [dd]: consider moving into domain
        if (!accountRepository.accountInExistence(accountCreationRequested.email())){
            AccountCreated accountCreated = Account.createAccount(accountCreationRequested);
            accountRepository.append(accountCreated);
            publisher.publish(accountCreated);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

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

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountModel> account(@RequestParam("email") final Email email) {
        //TODO [dd] add notNull contracts

        Optional<Account> maybeAccount = accountRepository.account(email);
        if (maybeAccount.isPresent()){
            Account account = maybeAccount.get();
            AccountModel accountModel = new AccountModel(account.email(), account.user());
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Override
    public void accept(Event<DomainEvent> event) {
        notNull(event);

        DomainEvent domainEvent = event.getData();
        if (domainEvent instanceof AccountCreationRequested) {
            accountCreationRequested((AccountCreationRequested) domainEvent);
        }
    }

    private void accountCreationRequested(AccountCreationRequested accountCreationRequested){
        notNull(accountCreationRequested);

        //TODO [dd]: consider moving into domain
        if (!accountRepository.accountInExistence(accountCreationRequested.email())){
            AccountCreated accountCreated = Account.createAccount(accountCreationRequested);
            accountRepository.append(accountCreated);
            publisher.publish(accountCreated);
        }
    }
}
