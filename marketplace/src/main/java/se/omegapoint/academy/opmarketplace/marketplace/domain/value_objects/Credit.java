package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import se.omegapoint.academy.opmarketplace.marketplace.MainConfiguration;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Credit {

    public static final int UPPER_LIMIT = 1000;

    public static final String ILLEGAL_FORMAT = "Illegal Format: Credit has to be an integer between 0 (inclusive) and " +
            UPPER_LIMIT + " (exclusive).";

    private final int amount;

    public Credit(int amount){
        notNull(amount);
        if (MainConfiguration.VALIDATION) {
            isTrue(amount >= 0 && amount < UPPER_LIMIT, ILLEGAL_FORMAT);
        }
        this.amount = amount;
    }

    public int amount(){
        return this.amount;
    }
}
