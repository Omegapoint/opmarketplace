package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Order {
    private final UUID id;
    private final UUID itemId;
    private final Quantity quantity;
    private final Credit sum;
    private final Email sellerId;
    private final Email buyerId;

    public Order(UUID itemId, Email sellerId, Quantity quantity, Credit sum, Email buyerId){
        this(UUID.randomUUID(), itemId, sellerId, quantity, sum, buyerId);
    }

    public Order(UUID id, UUID itemId, Email sellerId, Quantity quantity, Credit sum, Email buyerId){
        this.id = notNull(id);
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.sum = notNull(sum);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
    }

    public UUID id() {
        return id;
    }

    public UUID itemId() {
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
