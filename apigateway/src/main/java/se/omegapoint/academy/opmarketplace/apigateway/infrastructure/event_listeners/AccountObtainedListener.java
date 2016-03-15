package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotObtainedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountObtainedModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedListener implements Consumer<Event<JsonModel>> {
    private DeferredResult<AccountModel> result;

    public AccountObtainedListener(DeferredResult<AccountModel> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<JsonModel> event) {
        JsonModel model = event.getData();
        if (model instanceof AccountObtainedModel){
            result.setResult(((AccountObtainedModel)model).getAccount());
        }
        if (model instanceof AccountNotObtainedModel){
            result.setErrorResult(((AccountNotObtainedModel)model).getReason());
        }
    }
}
