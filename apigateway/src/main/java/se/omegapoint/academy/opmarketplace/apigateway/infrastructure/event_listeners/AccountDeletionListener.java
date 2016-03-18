package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.AccountDeletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.AccountNotDeletedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionListener implements Consumer<Event<se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event>> {
    private DeferredResult<ResponseEntity<String>> result;

    public AccountDeletionListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event> event) {
        notNull(event);
        se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event model = event.getData();
        if (model instanceof AccountDeletedDTO){
            result.setResult(ResponseEntity.ok(""));
        }
        if (model instanceof AccountNotDeletedDTO){
            result.setErrorResult(ResponseEntity.badRequest().body(((AccountNotDeletedDTO)model)));
        }
    }
}
