package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.factories;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notEmpty;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemFactory {
    private static Item item;

    private ItemFactory() {}

    public static Item fromPersistableEvents(List<PersistableEvent> events) {
        notEmpty(events);
        for (PersistableEvent e: events) {
            notNull(e);
            if (e instanceof ItemCreated)
                mutate((ItemCreated)e);
            else if (e instanceof ItemChanged){
                mutate((ItemChanged)e);
            }
        }
        return item;
    }

    private static void mutate(ItemCreated itemCreated) {
        item = new Item(itemCreated.item().id(),
                itemCreated.item().title(),
                itemCreated.item().description(),
                itemCreated.item().price(),
                itemCreated.item().supply());
    }

    private static void mutate(ItemChanged itemChanged) {
        item = new Item(itemChanged.item().id(),
                itemChanged.item().title(),
                itemChanged.item().description(),
                itemChanged.item().price(),
                itemChanged.item().supply());
    }
}
