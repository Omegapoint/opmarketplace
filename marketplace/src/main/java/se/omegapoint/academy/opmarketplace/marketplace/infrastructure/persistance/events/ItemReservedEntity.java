package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemReserved;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.sql.Timestamp;

@Entity
public class ItemReservedEntity implements Deserializer<ItemReserved> {

    @javax.persistence.Id
    @GeneratedValue
    private String id;
    private String itemId;
    private Integer quantity;
    private String reserverId;
    private Timestamp reservedUntil;
    private Timestamp time;

    protected ItemReservedEntity(){}

    public ItemReservedEntity(final String itemId, final int quantity, final String reserverId, final Timestamp reservedUntil, Timestamp time) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.reserverId = reserverId;
        this.reservedUntil = reservedUntil;
        this.time = time;
    }

    public ItemReservedEntity(ItemReserved itemReserved) {
        this.itemId = itemReserved.itemId().toString();
        this.quantity = itemReserved.quantity().amount();
        this.reserverId = itemReserved.reserver().address();
        this.reservedUntil = itemReserved.reservedUntil();
        this.time = itemReserved.timestamp();
    }

    public ItemReserved domainObject(){
         return new ItemReserved(new Id(itemId), new Quantity(quantity), new Email(reserverId), reservedUntil, time);
    }
}
