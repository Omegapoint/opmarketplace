package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountNotObtainedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountObtainedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedListener implements Consumer<Event<se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event>> {
    private DeferredResult<ResponseEntity<String>> result;
    ObjectMapper json = new ObjectMapper();

    public AccountObtainedListener(DeferredResult<ResponseEntity<String>> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event> event) {
        notNull(event);
        se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event model = event.getData();
        try {
            if (model instanceof AccountObtainedDTO){
                result.setResult(ResponseEntity.ok(json.writeValueAsString(((AccountObtainedDTO)model).account)));
            }
            if (model instanceof AccountNotObtainedDTO){
                result.setErrorResult(ResponseEntity.badRequest().body(((AccountNotObtainedDTO)model)));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
