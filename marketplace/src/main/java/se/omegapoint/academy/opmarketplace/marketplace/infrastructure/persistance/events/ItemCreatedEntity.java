package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemCreatedEntity implements Deserializer<ItemCreated> {

    @javax.persistence.Id
    @GeneratedValue
    private long eventId;
    private String id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT", length = 65535)
    private String description;
    private Integer price;
    private Integer supply;
    private String seller;
    private Timestamp time;

    protected ItemCreatedEntity(){}

    public ItemCreatedEntity(final String id, final String title, final String description, final int price, final int supply, final String seller, Timestamp time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.supply = supply;
        this.seller = seller;
        this.time = time;
    }

    public ItemCreated domainObject(){
        return new ItemCreated(new Item(
                new Id(id),
                new Title(title),
                new Description(description),
                new Credit(price),
                new Quantity(supply),
                new Email(seller)),
                time);
    }
}
