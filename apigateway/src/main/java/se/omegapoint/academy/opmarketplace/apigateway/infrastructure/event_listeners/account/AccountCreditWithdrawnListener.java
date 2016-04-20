package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountCreditDepositedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountCreditNotDepositedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountCreditNotWithdrawnDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountCreditWithdrawnDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditWithdrawnListener implements Consumer<reactor.bus.Event<Event>> {
    private DeferredResult<ResponseEntity<String>> result;

    public AccountCreditWithdrawnListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        notNull(event);
        Event model = event.getData();
        if (model instanceof AccountCreditWithdrawnDTO){
            result.setResult(ResponseEntity.ok(""));
        }else if (model instanceof AccountCreditNotWithdrawnDTO){
            result.setErrorResult(ResponseEntity.badRequest().body(((AccountCreditNotWithdrawnDTO)model)));
        } else{
            System.err.println("Response came through unknown event type: " + model.type());
        }
    }
}
