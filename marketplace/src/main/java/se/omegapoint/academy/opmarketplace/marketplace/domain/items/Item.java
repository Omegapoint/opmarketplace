package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import se.omegapoint.academy.opmarketplace.marketplace.domain.IdentifiedDomainObject;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.events.persistable.ItemCreated;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Item extends IdentifiedDomainObject {
    private final Title title;
    private final Description description;
    private final Price price;
    private final Timestamp expires;

    public Item(UUID id, Title title, Description description, Price price, Timestamp expires) {
        super(id);
        this.title = title;
        this.description = description;
        this.price = price;
        this.expires = expires;
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

    public static ItemCreated createItem(ItemCreationRequested request){
        notNull(request);
        return new ItemCreated(new Item(UUID.randomUUID(),
                request.title(),
                request.description(),
                request.price(),
                Timestamp.valueOf(LocalDateTime.now().plusWeeks(1))));
    }

    public Item changeTitle(String title){
        return new Item(UUID.fromString(this.id()), new Title(title), this.description, this.price, this.expires);
    }

    protected Timestamp expiration() {
        return expires;
    }

    public boolean hasExpired(){
        return false;
    }


}
