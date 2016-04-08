package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Order {
    private final UUID id;
    private final Credit sum;
    private final Email sellerId;
    private final Email buyerId;

    public Order(UUID id, Email sellerId, Credit sum, Email buyerId){
        this.id = notNull(id);
        this.sum = notNull(sum);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
    }

    public UUID id() {
        return id;
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
