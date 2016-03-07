package se.omegapoint.academy.opmarketplace.customer.application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.RepositoryResponse;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/accounts")
public class AccountService {
    @Autowired
    private AccountEventPublisher accountEventPublisher;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = POST)
    public ResponseEntity createAccount(@RequestBody final AccountRequestedModel newAccount) {
        try {
            new Email(newAccount.getEmail().getAddress());
            new User(newAccount.getUser().getFirstName(), newAccount.getUser().getLastName());
        } catch (IllegalArgumentValidationException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        Email emailId = new Email(newAccount.getEmail().getAddress());
        User user = new User(newAccount.getUser().getFirstName(), newAccount.getUser().getLastName());
        RepositoryResponse<Boolean> accountInExistenceResponse = accountRepository.accountInExistence(emailId);
        if (accountInExistenceResponse.isSuccess() && !accountInExistenceResponse.value()) {
            new Account(emailId, user, accountEventPublisher);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @RequestMapping(method = PUT)
    public ResponseEntity changeUser(@RequestParam("email") final String email, @RequestBody UserModel userModel) {
        try {
            new Email(email);
        } catch (IllegalArgumentValidationException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        Email emailId = new Email(email);
        RepositoryResponse<Account> accountToChangeResponse = accountRepository.account(emailId);
        if (accountToChangeResponse.isSuccess()){
            Account account = accountToChangeResponse.value();
            account.changeUser(userModel.getFirstName(), userModel.getLastName());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountModel> account(@RequestParam("email") final String email) {
        try {
            new Email(email);
        } catch (IllegalArgumentValidationException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Email emailId = new Email(email);
        RepositoryResponse<Account> response = accountRepository.account(emailId);
        if (response.isSuccess()){
            Account account = response.value();
            AccountModel accountModel = new AccountModel(account.email(), account.user());
            return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(accountModel);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
