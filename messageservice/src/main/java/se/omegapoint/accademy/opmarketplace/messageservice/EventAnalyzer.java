package se.omegapoint.accademy.opmarketplace.messageservice;


import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

public class EventAnalyzer implements Consumer<Event<DomainEventModel>> {

    public EventAnalyzer(EventBus eventBus) {
        eventBus.on(Selectors.object("event_posted"), this);
    }

    @Override
    public void accept(Event<DomainEventModel> testDataEvent) {
        System.out.println("Kolla, jag hittade ett event! DATA: " + testDataEvent.getData().getEventData());
    }
}
