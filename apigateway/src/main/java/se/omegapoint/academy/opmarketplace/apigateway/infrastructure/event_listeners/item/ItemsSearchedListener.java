package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemSearchCompletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemsNotSearchedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemsSearchedListener implements Consumer<reactor.bus.Event<Event>> {
    private DeferredResult<ResponseEntity<String>> result;
    ObjectMapper json = new ObjectMapper();

    public ItemsSearchedListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        notNull(event);
        Event model = event.getData();
        try {
            if (model instanceof ItemSearchCompletedDTO){
                result.setResult(ResponseEntity.ok(json.writeValueAsString(model)));
            }
            if (model instanceof ItemsNotSearchedDTO){
                result.setErrorResult(ResponseEntity.badRequest().body(((ItemsNotSearchedDTO)model)));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}