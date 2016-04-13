package se.omegapoint.academy.opmarketplace.marketplace.domain.services;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    DomainEvent item(Id id);

    DomainEvent search(String query);

    Optional<ItemOrdered> order(Id id);

    boolean itemInExistence(Id id);

    DomainEvent append(PersistableEvent event);
}
