package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;


import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreationRequested {
    private final Title title;
    private final Description description;
    private final Price price;
    private final Quantity supply;

    public ItemCreationRequested(Title title, Description description, Price price, Quantity supply){
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

    public Quantity supply() {
        return supply;
    }
}
