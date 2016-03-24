package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;


import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import java.sql.Timestamp;

public interface PersistableEvent extends DomainEvent {
    Timestamp timestamp();
}
