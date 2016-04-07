package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;

import java.sql.Timestamp;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrderReversed implements PersistableEvent {
    private final UUID originalOrderId;
    private final UUID itemId;
    private final Quantity quantity;
    private final Timestamp timestamp;

    public ItemOrderReversed(UUID originalOrderId, UUID itemId,Quantity quantity){
        this(originalOrderId, itemId, quantity, new Timestamp(System.currentTimeMillis()));
    }

    public ItemOrderReversed(UUID originalOrderId, UUID itemId, Quantity quantity, Timestamp timestamp){
        isTrue(notNull(timestamp).before(new Timestamp(System.currentTimeMillis() + 1)));
        this.originalOrderId = notNull(originalOrderId);
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.timestamp = notNull(timestamp);
    }

    public UUID originalOrderId(){
        return originalOrderId;
    }

    public UUID itemId(){
        return itemId;
    }

    public Quantity quantity(){
        return quantity;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}