package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Item;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreated implements PersistableEvent {

    private final Item item;
    private final Timestamp timestamp;

    public ItemCreated(Item item){
        this(item, new Timestamp(System.currentTimeMillis()));
    }

    public ItemCreated(Item item, Timestamp timestamp){
        isTrue(timestamp.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.item = notNull(item);
        this.timestamp = notNull(timestamp);
    }

    public Item item() {
        return item;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
