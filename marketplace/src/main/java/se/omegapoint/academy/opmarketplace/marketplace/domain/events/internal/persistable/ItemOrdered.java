package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;

import java.sql.Timestamp;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrdered implements PersistableEvent {
    private final UUID itemId;
    private final Quantity quantity;
    private final Credit price;
    private final Email sellerId;
    private final Email buyerId;
    private final Timestamp timestamp;

    public ItemOrdered(UUID itemId, Email sellerId, Quantity quantity, Credit price, Email buyerId){
        this(itemId, sellerId, quantity, price, buyerId, new Timestamp(System.currentTimeMillis()));
    }

    public ItemOrdered(UUID itemId, Email sellerId, Quantity quantity, Credit price, Email buyerId, Timestamp timestamp){
        isTrue(notNull(timestamp).before(new Timestamp(System.currentTimeMillis() + 1)));
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.price = notNull(price);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
        this.timestamp = notNull(timestamp);
    }

    public UUID itemId(){
        return itemId;
    }

    public Quantity quantity(){
        return quantity;
    }

    public Credit price(){
        return price;
    }

    public Email buyerId(){
        return buyerId;
    }

    public Email sellerId(){
        return sellerId;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
