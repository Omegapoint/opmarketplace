package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Router {

    private EventBus eventBus;

    public Router(EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(Event model){
        eventBus.notify(model.requestId(), reactor.bus.Event.wrap(model));
    }

    public void subscribe(String id, Consumer consumer){
        eventBus.on(Selectors.object(id), consumer).cancelAfterUse();
    }
}
