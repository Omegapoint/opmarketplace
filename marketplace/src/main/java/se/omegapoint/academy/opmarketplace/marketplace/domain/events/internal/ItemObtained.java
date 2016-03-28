package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemObtained implements DomainEvent {
    private final Item item;

    public ItemObtained(Item item) {
        this.item = notNull(item);
    }

    public Item item(){
        return this.item;
    }
}
