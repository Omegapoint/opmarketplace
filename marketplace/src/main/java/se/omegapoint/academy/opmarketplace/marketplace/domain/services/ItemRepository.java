package se.omegapoint.academy.opmarketplace.marketplace.domain.services;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemReserved;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Order;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    DomainEvent item(Id id);

    DomainEvent search(Query query);

    Optional<ItemOrdered> order(Id id);

    boolean itemInExistence(Id id);

    DomainEvent append(PersistableEvent event);

    List<ItemReserved> expiredReservationsSince(Email user, Timestamp since);

    List<ItemReserved> expiredReservationsSince(Id itemId, Timestamp since);

    Optional<ItemOrdered> lastOrderedItem(Email user);

    Optional<ItemOrdered> lastOrderedItem(Id itemId);
}
