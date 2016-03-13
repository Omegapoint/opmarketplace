package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.AccountCreatedListener;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.RemoteEvent;

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
    public DeferredResult<String> createAccount(@RequestBody final AccountRequestedModel newAccount) {
        notNull(newAccount);
        DeferredResult<String> result = new DeferredResult<>();
        publisher.publish(new RemoteEvent(newAccount, "AccountRequested"));
        eventBus.on(Selectors.object("Account"), new AccountCreatedListener(result, newAccount.getEmail()));
        return result;
    }
}
