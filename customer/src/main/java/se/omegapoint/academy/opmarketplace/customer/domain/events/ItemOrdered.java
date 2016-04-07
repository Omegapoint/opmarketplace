package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrdered implements DomainEvent {
    private final Credit price;
    private final Email sellerId;
    private final Email buyerId;

    public ItemOrdered(Email sellerId, Credit price, Email buyerId){
        this.price = notNull(price);
        this.buyerId = notNull(buyerId);
        this.sellerId = notNull(sellerId);
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
}
