package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.*;

public class Quantity {

    protected final static String ILLEGAL_VALUE = "Quantity cannot be less than 1.";

    private final int amount;

    public Quantity(int quantity) {
        isTrue(notNull(quantity) > 0);
        this.amount = quantity;
    }

    public int amount(){
        return this.amount;
    }
}
