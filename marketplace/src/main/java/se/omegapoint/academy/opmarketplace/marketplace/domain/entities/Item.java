package se.omegapoint.academy.opmarketplace.marketplace.domain.entities;

import se.omegapoint.academy.opmarketplace.marketplace.domain.IdentifiedDomainObject;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemChangeRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPurchaseRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Item extends IdentifiedDomainObject {
    private final Title title;
    private final Description description;
    private final Credit price;
    private final Quantity supply;
    private final Email seller;

    public Item(UUID id, Title title, Description description, Credit price, Quantity supply, Email seller) {
        super(notNull(id));
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Credit price() {
        return price;
    }

    public Quantity supply(){
        return supply;
    }

    public Email seller(){
        return seller;
    }

    public static ItemCreated createItem(ItemCreationRequested request){
        notNull(request);
        return new ItemCreated(new Item(UUID.randomUUID(),
                request.title(),
                request.description(),
                request.price(),
                request.supply(),
                request.seller()));
    }

    public ItemChanged handle(ItemChangeRequested request){
        isTrue(notNull(request).itemId().equals(id()));
        return new ItemChanged(new Item(request.itemId(),
                request.title(),
                request.description(),
                request.price(),
                request.supply(),
                this.seller));
    }

    public ItemOrdered handle(ItemPurchaseRequested request){
        isTrue(notNull(request).itemId().equals(id()));
        try {
            this.supply().remove(request.quantity());
        } catch (IllegalArgumentValidationException e){
            throw new IllegalArgumentValidationException("Insufficient supply.");
        }
        return new ItemOrdered(new Order(request.itemId(),
                seller(),
                request.quantity(),
                new Credit(price().amount() * request.quantity().amount()),
                request.buyer()));
    }
}
