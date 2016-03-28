package se.omegapoint.academy.opmarketplace.marketplace.infrastructure;


import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;

public class TestPublisher implements EventPublisher {
    private DomainEvent lastestEvent;
    @Override
    public void publish(DomainEvent event, String requestId) {
        lastestEvent = event;
    }

    public DomainEvent getLatestEvent(){
        return this.lastestEvent;
    }
}
