package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotObtainedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountUserNotChangedModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangedListener implements Consumer<Event<JsonModel>> {
    private DeferredResult<String> result;

    public AccountUserChangedListener(DeferredResult<String> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<JsonModel> event) {
        JsonModel model = event.getData();
        if (model instanceof AccountUserChangedModel){
            result.setResult("");
        }
        if (model instanceof AccountUserNotChangedModel){
            result.setErrorResult(((AccountUserNotChangedModel)model).getReason());
        }
    }
}
