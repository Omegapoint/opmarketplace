package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

import java.util.concurrent.atomic.AtomicInteger;

public class Analyzer implements Consumer<Event<DomainEventModel>> {

    EventBus eventBus;
    AtomicInteger counter;

    public Analyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.on(Selectors.object("events"), this);
        counter = new AtomicInteger();
    }

    @Override
    public void accept(Event<DomainEventModel> domainEventModelEvent) {
        System.out.println("Received and analyzing event...");
        if (counter.get() > 10) {
            eventBus.notify("command", Event.wrap(new CommandEvent("AccountCreated", false)));
        }
        counter.incrementAndGet();
    }
}
