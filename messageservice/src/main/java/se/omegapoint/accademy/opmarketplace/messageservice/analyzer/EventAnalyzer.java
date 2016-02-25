package se.omegapoint.accademy.opmarketplace.messageservice.analyzer;


import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.models.Channels;
import se.omegapoint.accademy.opmarketplace.messageservice.models.DomainEventModel;

import java.util.concurrent.atomic.AtomicInteger;

public class EventAnalyzer implements Consumer<Event<DomainEventModel>> {

    EventBus eventBus;
    AtomicInteger counter;

    public EventAnalyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.on(Selectors.object(Channels.EVENTS), this);
        counter = new AtomicInteger(0);
    }

    @Override
    public void accept(Event<DomainEventModel> testDataEvent) {
        System.out.println("Kolla, jag hittade ett event! DATA: " + testDataEvent.getData().getEventData());
        if (counter.incrementAndGet() > 10) {
            eventBus.notify(Channels.RULE_COMMAND, Event.wrap(false));
            System.out.println("NO MORE! I HAVE HAD ENOUGH!");
        }
    }
}
