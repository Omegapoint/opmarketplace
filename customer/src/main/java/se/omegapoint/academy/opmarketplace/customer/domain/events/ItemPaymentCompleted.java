package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentCompleted implements DomainEvent{

    private final Email sellerId;
    private final Credit price;
    private final Email buyerId;

    public ItemPaymentCompleted(Email sellerId, Credit price, Email buyerId) {
        this.sellerId = notNull(sellerId);
        this.price = notNull(price);
        this.buyerId = notNull(buyerId);
    }

    public Email sellerId() {
        return sellerId;
    }

    public Credit price() {
        return price;
    }

    public Email buyerId() {
        return buyerId;
    }
}
