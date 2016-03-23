package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.persistable;


import se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.DomainEvent;

import java.sql.Timestamp;

public interface PersistableEvent extends DomainEvent {
    Timestamp timestamp();
}
