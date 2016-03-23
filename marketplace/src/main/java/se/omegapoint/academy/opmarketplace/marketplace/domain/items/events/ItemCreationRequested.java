package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events;


import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Title;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreationRequested {
    private final Title title;
    private final Description description;
    private final Price price;

    public ItemCreationRequested(Title title, Description description, Price price){
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
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
}
