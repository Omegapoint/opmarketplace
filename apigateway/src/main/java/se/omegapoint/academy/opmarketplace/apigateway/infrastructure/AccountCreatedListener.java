package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.EmailModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedListener implements Consumer<Event<AccountCreatedModel>> {
    private DeferredResult<String> result;
    private final EmailModel id;

    public AccountCreatedListener(DeferredResult<String> result, EmailModel id) {
        notNull(result);
        notNull(id);
        this.result = result;
        this.id = id;
    }

    @Override
    public void accept(Event<AccountCreatedModel> accountCreatedModelEvent) {
        if (this.id.getAddress().equals(accountCreatedModelEvent.getData().getEmail().getAddress())){
            result.setResult("Created");
            // TODO: 3/13/2016 Unsubscribe from EventBus
        }
    }
}
