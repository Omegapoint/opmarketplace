package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.AccountCreatedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.AccountObtainedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@RestController
@RequestMapping("/accounts")
public class AccountGateway {

    @Autowired
    EventBus eventBus;

    @Autowired
    EventRemotePublisherService publisher;

    @RequestMapping(method = POST)
    public DeferredResult<String> createAccount(@RequestBody final AccountCreationRequestedModel newAccount) {
        notNull(newAccount);
        DeferredResult<String> result = new DeferredResult<>();
        publisher.publish(new RemoteEvent(newAccount, AccountCreationRequestedModel.TYPE));
        eventBus.on(Selectors.object(AccountCreatedModel.TYPE + newAccount.getEmail().getAddress()),
                new AccountCreatedListener(result)).cancelAfterUse();
        return result;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public DeferredResult<AccountModel> account(@RequestParam("email") final EmailModel email) {
        AccountRequestedModel accountRequested = new AccountRequestedModel(notNull(email));
        DeferredResult<AccountModel> result = new DeferredResult<>();
        publisher.publish(new RemoteEvent(accountRequested, AccountRequestedModel.TYPE));
        eventBus.on(Selectors.object(AccountObtainedModel.TYPE + accountRequested.getEmail().getAddress()),
                new AccountObtainedListener(result)).cancelAfterUse();
        return result;
    }
}
