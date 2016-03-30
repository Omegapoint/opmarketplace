package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemSearchResult implements DomainEvent {
    private final List<Item> items;

    public ItemSearchResult(List<Item> items) {
        this.items = notNull(items);
    }

    public List<Item> items(){
        return this.items;
    }
}
