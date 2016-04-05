package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemChangedEntity implements Deserializer<ItemChanged>{

    @Id
    @GeneratedValue
    private long eventId;
    private String id;
    private String title;
    private String description;
    private Integer price;
    private Integer supply;
    private Timestamp time;

    protected ItemChangedEntity(){}

    public ItemChangedEntity(final String id, final String title, final String description, final int price, final int supply, Timestamp time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.supply = supply;
        this.time = time;
    }

    public ItemChanged domainObject(){
        return new ItemChanged(new Item(UUID.fromString(id),
                new Title(title),
                new Description(description),
                new Credit(price),
                new Quantity(supply)), time);
    }
}
