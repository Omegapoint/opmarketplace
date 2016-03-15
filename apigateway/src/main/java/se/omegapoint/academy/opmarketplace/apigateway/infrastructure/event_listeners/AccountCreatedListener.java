package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotCreatedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedListener implements Consumer<Event<JsonModel>> {
    private DeferredResult<String> result;

    public AccountCreatedListener(DeferredResult<String> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<JsonModel> event) {
        JsonModel model = event.getData();
        if (model instanceof AccountCreatedModel){
            result.setResult("");
        }
        if (model instanceof AccountNotCreatedModel){
            result.setErrorResult(((AccountNotCreatedModel)model).getReason());
        }
    }
}