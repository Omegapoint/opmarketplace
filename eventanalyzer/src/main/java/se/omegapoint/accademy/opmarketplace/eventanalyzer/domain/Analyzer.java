package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.util.HashMap;

public class Analyzer implements Consumer<Event<RemoteEvent>> {

    EventBus eventBus;
    HashMap<String, Integer> eventCounter;

    public Analyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.on(Selectors.object("events"), this);
        eventCounter = new HashMap<>();
    }

    @Override
    public void accept(Event<RemoteEvent> domainEventModelEvent) {
        System.out.println("Received and analyzing event...");
        RemoteEvent domainEvent = domainEventModelEvent.getData();
        String eventType = domainEvent.getType();
        if (!eventCounter.containsKey(eventType)) {
            eventCounter.put(eventType, 0);
        } else {
            Integer current = eventCounter.get(eventType);
            eventCounter.put(eventType, current + 1);
        }

        if (eventCounter.get(eventType) > 10) {
            eventBus.notify("command", Event.wrap(new CommandEvent(eventType, false)));
        }
    }
}
