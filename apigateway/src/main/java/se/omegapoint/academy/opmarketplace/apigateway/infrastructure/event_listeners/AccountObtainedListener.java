package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotObtainedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountObtainedModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedListener implements Consumer<Event<JsonModel>> {
    private DeferredResult<ResponseEntity<String>> result;
    ObjectMapper json = new ObjectMapper();

    public AccountObtainedListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<JsonModel> event) {
        JsonModel model = event.getData();
        try {
            if (model instanceof AccountObtainedModel){
                result.setResult(ResponseEntity.ok(json.writeValueAsString(((AccountObtainedModel)model).getAccount())));
            }
            if (model instanceof AccountNotObtainedModel){
                result.setErrorResult(ResponseEntity.badRequest().body(((AccountNotObtainedModel)model).getReason()));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
