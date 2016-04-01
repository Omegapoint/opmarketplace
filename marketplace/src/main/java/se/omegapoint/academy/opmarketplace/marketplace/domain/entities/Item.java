package se.omegapoint.academy.opmarketplace.marketplace.domain.entities;

import se.omegapoint.academy.opmarketplace.marketplace.domain.IdentifiedDomainObject;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemChangeRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Item extends IdentifiedDomainObject {
    private final Title title;
    private final Description description;
    private final Price price;
    private final Quantity supply;

    public Item(UUID id, Title title, Description description, Price price, Quantity supply) {
        super(notNull(id));
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
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

    public Quantity supply(){
        return supply;
    }

    public static ItemCreated createItem(ItemCreationRequested request){
        notNull(request);
        return new ItemCreated(new Item(UUID.randomUUID(),
                request.title(),
                request.description(),
                request.price(),
                request.supply()));
    }

    public ItemChanged handle(ItemChangeRequested request){
        isTrue(notNull(request).itemId().equals(id()));
        return new ItemChanged(new Item(UUID.randomUUID(),
                request.title(),
                request.description(),
                request.price(),
                request.supply()));
    }
}
