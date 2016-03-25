package se.omegapoint.academy.opmarketplace.marketplace.domain.services;


import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event, String requestId);
}
