package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.Router;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.AccountCreatedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.AccountDeletionListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.AccountObtainedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.AccountUserChangedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/accounts")
public class AccountGateway {

    private final long TIMEOUT = 2000;
    private final ResponseEntity<String> TIMEOUT_RESPONSE = ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("");

    @Autowired
    private Router router;

    @Autowired
    private RemoteEventPublisher publisher;

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> createAccount(@RequestBody final AccountCreationRequestedDTO newAccount) {
        notNull(newAccount);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        publisher.publish(new OutgoingRemoteEvent(newAccount));
        AccountCreatedListener listener =  new AccountCreatedListener(result);
        router.subscribe(Router.CHANNEL.ACCOUNTCREATION, newAccount.email.address, listener);
        return result;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> account(@RequestParam("email") final EmailDTO email) {
        AccountRequestedDTO accountRequested = new AccountRequestedDTO(notNull(email));
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        publisher.publish(new OutgoingRemoteEvent(accountRequested));
        AccountObtainedListener listener =  new AccountObtainedListener(result);
        router.subscribe(Router.CHANNEL.ACCOUNTREQUEST, accountRequested.email.address, listener);
        return result;
    }

    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> changeUser(@RequestBody final AccountUserChangeRequestedDTO change) {
        notNull(change);
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        publisher.publish(new OutgoingRemoteEvent(change));
        AccountUserChangedListener listener =  new AccountUserChangedListener(result);
        router.subscribe(Router.CHANNEL.ACCOUNTUSERCHANGE, change.email.address, listener);
        return result;
    }

    @RequestMapping(method = DELETE, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<String>> deleteAccount(@RequestParam("email") final EmailDTO email) {
        AccountDeletionRequestedDTO accountDeletionRequested = new AccountDeletionRequestedDTO(notNull(email));
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(TIMEOUT, TIMEOUT_RESPONSE);
        publisher.publish(new OutgoingRemoteEvent(accountDeletionRequested));
        AccountDeletionListener listener =  new AccountDeletionListener(result);
        router.subscribe(Router.CHANNEL.ACCOUNTREQUEST, accountDeletionRequested.email.address, listener);
        return result;
    }
}
