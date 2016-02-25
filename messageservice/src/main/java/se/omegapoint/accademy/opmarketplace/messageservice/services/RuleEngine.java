package se.omegapoint.accademy.opmarketplace.messageservice.services;


import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.models.Channels;
import se.omegapoint.accademy.opmarketplace.messageservice.models.DomainEventModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class RuleEngine implements Consumer<Event<Boolean>> {

    AtomicBoolean allowEvents;

    public RuleEngine(EventBus eventBus) {
        eventBus.on(Selectors.object(Channels.RULE_COMMAND), this);
        allowEvents = new AtomicBoolean(true);
    }

    public boolean allow(DomainEventModel event) {
        return allowEvents.get();
    }

    @Override
    public void accept(Event<Boolean> booleanEvent) {
        allowEvents.set(booleanEvent.getData());
    }
}
