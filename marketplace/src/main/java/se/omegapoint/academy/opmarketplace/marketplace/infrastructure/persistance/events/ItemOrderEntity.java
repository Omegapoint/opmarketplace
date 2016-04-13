package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemOrderEntity implements Deserializer<ItemOrdered> {

    @javax.persistence.Id
    private String orderId;
    private String id;
    private Integer quantity;
    private Integer sum;
    private String sellerId;
    private String buyerId;
    private Timestamp time;

    protected ItemOrderEntity(){}

    public ItemOrderEntity(final String orderId, final String id, final int quantity, final int sum,  final String sellerId, final String buyerId, Timestamp time) {
        this.orderId = orderId;
        this.id = id;
        this.quantity = quantity;
        this.sum = sum;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.time = time;
    }

    public ItemOrderEntity(ItemOrdered itemOrdered) {
        this.orderId = itemOrdered.order().id().toString();
        this.id = itemOrdered.order().itemId().toString();
        this.quantity = itemOrdered.order().quantity().amount();
        this.sum = itemOrdered.order().sum().amount();
        this.sellerId = itemOrdered.order().sellerId().address();
        this.buyerId = itemOrdered.order().buyerId().address();
        this.time = itemOrdered.timestamp();
    }

    public ItemOrdered domainObject(){
        return new ItemOrdered(new Order(new Id(orderId),
                new Id(id),
                new Email(sellerId),
                new Quantity(quantity),
                new Credit(sum),
                new Email(buyerId)),
                time);
    }
}
