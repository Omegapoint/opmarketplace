package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemRequested implements DomainEvent {

    private final Id itemId;

    public ItemRequested(Id itemId) {
        this.itemId = notNull(itemId);
    }

    public Id itemId(){
        return this.itemId;
    }
}
