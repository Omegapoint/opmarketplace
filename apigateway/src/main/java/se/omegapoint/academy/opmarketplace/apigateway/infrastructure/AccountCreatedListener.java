package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountCreatedModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedListener implements Consumer<Event<AccountCreatedModel>> {
    private DeferredResult<String> result;

    public AccountCreatedListener(DeferredResult<String> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<AccountCreatedModel> accountCreatedModelEvent) {
        result.setResult("Created");
    }
}
