package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemChangeRequested {
    private final Id itemId;
    private final Title title;
    private final Description description;
    private final Credit price;
    private final Quantity supply;

    public ItemChangeRequested(Id itemId, Title title, Description description, Credit price, Quantity supply){
        this.itemId = notNull(itemId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
    }

    public Id itemId(){
        return itemId;
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

    public Quantity supply() {
        return supply;
    }
}
