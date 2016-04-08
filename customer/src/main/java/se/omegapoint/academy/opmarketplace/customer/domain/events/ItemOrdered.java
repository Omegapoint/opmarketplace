package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Order;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrdered implements DomainEvent {

    private final Order order;

    public ItemOrdered(Order order){
        this.order = notNull(order);
    }

    public Order order(){
        return this.order;
    }
}
