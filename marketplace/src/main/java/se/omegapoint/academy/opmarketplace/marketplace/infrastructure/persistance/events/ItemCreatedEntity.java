package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Title;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.persistable.ItemCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ItemCreatedEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String price;
    private Timestamp time;

    protected ItemCreatedEntity(){}

    public ItemCreatedEntity(final String id, final String title, final String description, final String price, Timestamp time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.time = time;
    }

    public ItemCreated domainEvent(){
        return new ItemCreated(new Item(UUID.fromString(id),
                new Title(title),
                new Description(description),
                new Price(price), time));
    }
}
