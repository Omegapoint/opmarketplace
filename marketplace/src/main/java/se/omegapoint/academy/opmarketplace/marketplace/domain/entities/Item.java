package se.omegapoint.academy.opmarketplace.marketplace.domain.entities;

import se.omegapoint.academy.opmarketplace.marketplace.domain.IdentifiedDomainObject;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Expiration;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Item extends IdentifiedDomainObject {
    private final Title title;
    private final Description description;
    private final Price price;
    private final Expiration expires;

    public Item(UUID id, Title title, Description description, Price price, Expiration expires) {
        super(notNull(id));
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.expires = notNull(expires);
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Price price() {
        return price;
    }

    public Expiration expiration() {
        return this.expires;
    }

    public boolean hasExpired(){
        return this.expires.hasExpired();
    }

    public static ItemCreated createItem(ItemCreationRequested request){
        notNull(request);
        return new ItemCreated(new Item(UUID.randomUUID(),
                request.title(),
                request.description(),
                request.price(),
                new Expiration(Timestamp.valueOf(LocalDateTime.now().plusWeeks(1)))));
    }

    public Item changeTitle(String title){
        return new Item(UUID.fromString(this.id()), new Title(title), this.description, this.price, this.expires);
    }
}
