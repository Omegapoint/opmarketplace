package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemPaymentCompletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemPaymentNotCompletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemNotOrderedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseListener implements Consumer<reactor.bus.Event<Event>> {
    private DeferredResult<ResponseEntity<String>> result;

    public ItemPurchaseListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        notNull(event);
        Event model = event.getData();
        if (model instanceof ItemPaymentCompletedDTO) {
            result.setResult(ResponseEntity.ok(""));
        } else if (model instanceof ItemPaymentNotCompletedDTO) {
            result.setErrorResult(ResponseEntity.badRequest().body(((ItemPaymentNotCompletedDTO) model)));
        } else if (model instanceof ItemNotOrderedDTO) {
            result.setErrorResult(ResponseEntity.badRequest().body(((ItemNotOrderedDTO) model)));
        } else {
            System.err.println("Response came through unknown event type: " + model.type());
        }
    }
}
