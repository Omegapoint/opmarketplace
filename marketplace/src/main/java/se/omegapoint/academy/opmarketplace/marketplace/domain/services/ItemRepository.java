package se.omegapoint.academy.opmarketplace.marketplace.domain.services;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> item(String id);

    boolean itemInExistence(String id);

    void append(PersistableEvent event);
}
