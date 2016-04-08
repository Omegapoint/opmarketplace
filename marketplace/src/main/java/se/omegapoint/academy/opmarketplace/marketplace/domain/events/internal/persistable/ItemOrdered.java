package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Order;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;

import java.sql.Timestamp;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrdered implements PersistableEvent {
    private final Order order;
    private final Timestamp timestamp;

    public ItemOrdered(Order order){
        this(order, new Timestamp(System.currentTimeMillis()));
    }

    public ItemOrdered(Order order, Timestamp timestamp){
        isTrue(notNull(timestamp).before(new Timestamp(System.currentTimeMillis() + 1)));
        this.order = order;
        this.timestamp = notNull(timestamp);
    }

    public Order order(){
        return this.order;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
