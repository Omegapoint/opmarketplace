package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.persistable.PersistableEvent;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    Optional<Item> item(String id);

    boolean itemInExistence(String id);

    void append(PersistableEvent event);
}
