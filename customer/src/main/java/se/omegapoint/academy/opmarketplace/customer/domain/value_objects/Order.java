package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Order {
    private final Id id;
    private final Credit sum;
    private final Email sellerId;
    private final Email buyerId;

    public Order(Id id, Email sellerId, Credit sum, Email buyerId){
        this.id = notNull(id);
        this.sum = notNull(sum);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
    }

    public Id id() {
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
