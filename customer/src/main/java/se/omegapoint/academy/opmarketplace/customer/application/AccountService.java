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
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
    public void accept(Event<JsonModel> event) {
        notNull(event);

        JsonModel jsonModel = event.getData();
        if (jsonModel instanceof AccountCreationRequestedModel) {
            accountCreationRequested((AccountCreationRequestedModel) jsonModel);
        }
    }

    private void accountCreationRequested(AccountCreationRequestedModel accountCreationRequestedModel){
        try {
            AccountCreationRequested request = accountCreationRequestedModel.domainObject();

            if (!accountRepository.accountInExistence(request.email())){
                AccountCreated accountCreated = Account.createAccount(request);
                accountRepository.append(accountCreated);
                publisher.publish(accountCreated);
            }

        } catch (IllegalArgumentValidationException e) {
            // TODO: 15/03/16 Send user not created event with reason
            e.printStackTrace();
        }
    }
}
