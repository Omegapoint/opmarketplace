package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemNotReservedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemReservedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReservationListener implements Consumer<reactor.bus.Event<Event>> {

    DeferredResult<ResponseEntity<String>> result;

    public ItemReservationListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        Event model = notNull(event).getData();
        if (model instanceof ItemReservedDTO) {
            result.setResult(ResponseEntity.ok(""));
        } else if (model instanceof ItemNotReservedDTO) {
            result.setErrorResult(ResponseEntity.badRequest().body((ItemNotReservedDTO) model));
        } else {
            System.err.println("Response came through unknown event type: " + model.type());
        }
    }
}
