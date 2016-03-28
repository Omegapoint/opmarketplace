package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemCreatedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemNotCreatedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreatedListener implements Consumer<reactor.bus.Event<Event>> {
    private DeferredResult<ResponseEntity<String>> result;

    public ItemCreatedListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        notNull(event);
        Event model = event.getData();
        if (model instanceof ItemCreatedDTO){
            result.setResult(ResponseEntity.ok(""));
        }
        if (model instanceof ItemNotCreatedDTO){
            result.setErrorResult(ResponseEntity.badRequest().body(((ItemNotCreatedDTO)model)));
        }
    }
}
