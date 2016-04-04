package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import reactor.bus.EventBus;
import reactor.bus.registry.Registration;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.application.ItemGateway;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.event_listeners.TimeoutListenerCleanup;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import java.util.Timer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Router {

    private static final long TIMEOUT = ItemGateway.TIMEOUT + 1000;

    private Timer timeoutCleanup;

    private EventBus eventBus;

    public Router(EventBus eventBus, Timer timeoutCleanup) {
        this.eventBus = notNull(eventBus);
        this.timeoutCleanup = timeoutCleanup;
    }

    public void publish(Event model){
        eventBus.notify(model.requestId(), reactor.bus.Event.wrap(model));
    }

    public void subscribe(String id, Consumer consumer){
        Registration registrationToCancel = eventBus.on(Selectors.object(id), consumer).cancelAfterUse();
        timeoutCleanup.schedule(new TimeoutListenerCleanup(registrationToCancel), TIMEOUT);
    }
}
