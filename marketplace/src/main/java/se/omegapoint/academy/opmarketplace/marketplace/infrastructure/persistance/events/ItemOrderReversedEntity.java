package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrderReversed;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemOrderReversedEntity implements Deserializer<ItemOrderReversed> {

    @Id
    @GeneratedValue
    private long eventId;
    private String originalOrderId;
    private String itemId;
    private Integer quantity;
    private Timestamp time;

    protected ItemOrderReversedEntity(){}

    public ItemOrderReversedEntity(final String originalOrderId, final String itemId, final int quantity, Timestamp time) {
        this.originalOrderId = originalOrderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.time = time;
    }

    public ItemOrderReversed domainObject(){
        return new ItemOrderReversed(UUID.fromString(originalOrderId),
                UUID.fromString(itemId),
                new Quantity(quantity),
                time);
    }
}
