package se.omegapoint.academy.opmarketplace.customer.application;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;


import static reactor.bus.selector.Selectors.$;

public class EventPublisherService implements Consumer<Event<DomainEvent>> {

    public EventPublisherService(EventBus eventBus){
        eventBus.on($(), this);
    }

    @Override
    public void accept(Event<DomainEvent> event) {

    }
}
