package se.omegapoint.academy.opmarketplace.customer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.AccountEventStore;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/accounts")
public class AccountRestService {
    @Autowired
    EventBus eventBus;

    @Autowired
    AccountEventStore accountEventStore;

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity createAccount(@RequestParam("email") final String email, @RequestParam("first-eventType") final String firstName, @RequestParam("last-eventType") final String lastName) {
        try {
            new Account(email, firstName, lastName, new AccountEventPublisherService(eventBus));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException | IllegalArgumentValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity changeFirstname(@RequestParam("email") final String email, @RequestParam("first-eventType") final String firstName) {
        try {
            accountEventStore.account(email).changeFirstName(firstName);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException | IllegalArgumentValidationException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
