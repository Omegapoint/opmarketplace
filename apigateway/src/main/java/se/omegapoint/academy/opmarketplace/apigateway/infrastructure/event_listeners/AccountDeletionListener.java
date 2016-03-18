package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountDeletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotDeletedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionListener implements Consumer<Event<JsonModel>> {
    private DeferredResult<ResponseEntity<String>> result;

    public AccountDeletionListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<JsonModel> event) {
        notNull(event);
        JsonModel model = event.getData();
        if (model instanceof AccountDeletedDTO){
            result.setResult(ResponseEntity.ok(""));
        }
        if (model instanceof AccountNotDeletedDTO){
            result.setErrorResult(ResponseEntity.badRequest().body(((AccountNotDeletedDTO)model)));
        }
    }
}
