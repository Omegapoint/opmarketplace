package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseRequested {
    private Id itemId;
    private Quantity quantity;
    private Email buyer;

    public ItemPurchaseRequested(Id itemId, Quantity quantity, Email buyer) {
        this.buyer = notNull(buyer);
        this.quantity = notNull(quantity);
        this.itemId = notNull(itemId);
    }

    public Id itemId() {
        return itemId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Email buyer() {
        return buyer;
    }
}
