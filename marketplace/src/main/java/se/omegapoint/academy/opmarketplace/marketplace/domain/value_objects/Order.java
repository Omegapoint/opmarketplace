package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Order {
    private final Id id;
    private final Id itemId;
    private final Quantity quantity;
    private final Credit sum;
    private final Email sellerId;
    private final Email buyerId;

    public Order(Id itemId, Email sellerId, Quantity quantity, Credit sum, Email buyerId){
        this(new Id(), itemId, sellerId, quantity, sum, buyerId);
    }

    public Order(Id id, Id itemId, Email sellerId, Quantity quantity, Credit sum, Email buyerId){
        this.id = notNull(id);
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.sum = notNull(sum);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
    }

    public Id id() {
        return id;
    }

    public Id itemId() {
        return itemId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Credit sum() {
        return sum;
    }

    public Email sellerId() {
        return sellerId;
    }

    public Email buyerId() {
        return buyerId;
    }
}
