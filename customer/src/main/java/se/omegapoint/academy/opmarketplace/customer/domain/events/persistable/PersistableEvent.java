package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;


import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.sql.Timestamp;

public interface PersistableEvent extends DomainEvent{
    Timestamp timestamp();
}
