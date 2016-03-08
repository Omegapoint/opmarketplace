package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
