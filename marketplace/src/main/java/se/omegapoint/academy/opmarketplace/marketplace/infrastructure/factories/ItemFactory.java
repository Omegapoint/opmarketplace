package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.factories;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.*;

import java.sql.Timestamp;
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
            } else if (e instanceof ItemOrdered){
                mutate((ItemOrdered)e);
            } else if (e instanceof ItemOrderReversed){
                mutate((ItemOrderReversed)e);
            } else if (e instanceof ItemReserved) {
                mutate((ItemReserved) e);
            }
        }
        return item;
    }

    private static void mutate(ItemOrderReversed itemOrderReversed) {
        item = new Item(item.id(),
                item.title(),
                item.description(),
                item.price(),
                item.supply().add(itemOrderReversed.quantity()),
                item.seller());
    }

    private static void mutate(ItemOrdered itemOrdered) {
        item = new Item(item.id(),
                item.title(),
                item.description(),
                item.price(),
                item.supply().remove(itemOrdered.order().quantity()),
                item.seller());
    }

    private static void mutate(ItemCreated itemCreated) {
        item = new Item(itemCreated.item().id(),
                itemCreated.item().title(),
                itemCreated.item().description(),
                itemCreated.item().price(),
                itemCreated.item().supply(),
                itemCreated.item().seller());
    }

    private static void mutate(ItemChanged itemChanged) {
        item = new Item(itemChanged.item().id(),
                itemChanged.item().title(),
                itemChanged.item().description(),
                itemChanged.item().price(),
                itemChanged.item().supply(),
                itemChanged.item().seller());
    }

    private static void mutate(ItemReserved itemReserved) {
        if (itemReserved.reservedUntil().after(new Timestamp(System.currentTimeMillis()))) {
            item = new Item(item.id(),
                    item.title(),
                    item.description(),
                    item.price(),
                    item.supply().remove(itemReserved.quantity()),
                    item.seller());
        }
    }
}
