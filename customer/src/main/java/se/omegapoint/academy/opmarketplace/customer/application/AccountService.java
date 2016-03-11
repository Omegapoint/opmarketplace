package se.omegapoint.academy.opmarketplace.customer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/accounts")
public class AccountService {

    @Autowired
    private EventPublisher publisher;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = POST)
    public ResponseEntity createAccount(@RequestBody final AccountRequestedModel newAccount) {
        Result<AccountRequested> result = newAccount.domainObject();
        if (result.isSuccess()) {
            AccountRequested accountRequested = result.value();
            Result<Boolean> accountInExistence = accountRepository.accountInExistence(accountRequested.email());

            //TODO [dd]: consider moving into domain
            if (accountInExistence.isSuccess() && !accountInExistence.value()){
                AccountCreated accountCreated = Account.requestAccount(accountRequested);
                accountRepository.append(accountCreated);
                publisher.publish(accountCreated);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //TODO [dd]: is this method really idempotent? PUT operations must be idempotent according to the specification. See https://spring.io/understanding/REST
    @RequestMapping(method = PUT)
    public ResponseEntity changeUser(@RequestParam("email") final Email email, @RequestBody UserModel userModel) {
        Result<Account> accountToChange = accountRepository.account(email);
        if (accountToChange.isSuccess()){
            Account account = accountToChange.value();
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
        Result<Account> response = accountRepository.account(email);
        if (response.isSuccess()){
            Account account = response.value();
            //TODO [dd]: creation of json objects should be done in the infrastructure
            AccountModel accountModel = new AccountModel(account.email(), account.user());
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
