package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;

import java.sql.Timestamp;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreated implements PersistableEvent {

    private final Item item;
    private final Timestamp timestamp;

    public ItemCreated(Item item){
        this(item, new Timestamp(System.currentTimeMillis()));
    }

    public ItemCreated(Item item, Timestamp timestamp){
        isTrue(notNull(timestamp).before(new Timestamp(System.currentTimeMillis() + 1)));
        this.item = notNull(item);
        this.timestamp = notNull(timestamp);
    }

    public Item item() {
        return item;
    }

    public Id itemId(){
        return item.id();
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
