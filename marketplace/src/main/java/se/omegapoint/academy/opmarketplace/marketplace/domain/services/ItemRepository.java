package se.omegapoint.academy.opmarketplace.marketplace.domain.services;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    DomainEvent item(UUID id);

    boolean itemInExistence(UUID id);

    DomainEvent append(PersistableEvent event);
}
