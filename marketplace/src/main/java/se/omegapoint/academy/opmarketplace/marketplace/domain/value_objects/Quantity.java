package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import se.omegapoint.academy.opmarketplace.marketplace.MainConfiguration;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.*;

public class Quantity {

    protected final static String ILLEGAL_VALUE = "Illegal Format: Quantity cannot be less than 0.";

    private final int amount;

    public Quantity(int quantity) {
        notNull(quantity);
        if (MainConfiguration.VALIDATION) {
            isTrue(quantity >= 0, ILLEGAL_VALUE);
        }
        this.amount = quantity;
    }

    public int amount(){
        return this.amount;
    }

    public Quantity remove(Quantity toRemove){
        return new Quantity(amount - toRemove.amount);
    }

    public Quantity add(Quantity toAdd){
        return new Quantity(amount + toAdd.amount);
    }
}
