package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReservationRequested {
    private Id itemId;
    private Quantity quantity;
    private Email reserver;

    public ItemReservationRequested(Id itemId, Quantity quantity, Email reserver) {
        this.reserver = notNull(reserver);
        this.quantity = notNull(quantity);
        this.itemId = notNull(itemId);
    }

    public Id itemId() {
        return itemId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Email reserver() {
        return reserver;
    }
}
