package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemOrderEntity implements Deserializer<ItemOrdered> {

    @Id
    private String orderId;
    private String id;
    private Integer quantity;
    private Integer price;
    private String sellerId;
    private String buyerId;
    private Timestamp time;

    protected ItemOrderEntity(){}

    public ItemOrderEntity(final String orderId, final String id, final int quantity, final int price,  final String sellerId, final String buyerId, Timestamp time) {
        this.orderId = orderId;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.time = time;
    }

    public ItemOrdered domainObject(){
        return new ItemOrdered(UUID.fromString(orderId),
                UUID.fromString(id),
                new Email(sellerId),
                new Quantity(quantity),
                new Credit(price),
                new Email(buyerId),
                time);
    }
}
