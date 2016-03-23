package se.omegapoint.academy.opmarketplace.customer.domain.services;

import se.omegapoint.academy.opmarketplace.customer.domain.events.*;

public interface EventPublisher {
    void publish(DomainEvent event, String requestId);
}
