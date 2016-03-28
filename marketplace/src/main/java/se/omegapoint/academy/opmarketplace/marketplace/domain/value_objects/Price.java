package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public final class Price {

    protected static final int EXPONENT_LIMIT = 9;

    public static final String ILLEGAL_FORMAT = "Price can only contain digits and one '.'. It can be no larger than 1 000 000 000 and have no more than 2 decimal places.";

    private final long amount;

    public Price(String amount) {
        notBlank(amount);
        isTrue(amount.length() <= EXPONENT_LIMIT +3 && amount.matches("\\d{1," + EXPONENT_LIMIT + "}(\\.\\d{1,2})?"), ILLEGAL_FORMAT);

        if (amount.matches(".*\\.\\d")) {
            amount += "0";
        } else if (!amount.contains(".")) {
            amount += "00";
        }

        amount = amount.replace(".", "");
        this.amount = Long.parseLong(amount);
    }

    public String amount(){
        String amountString = Long.toString(amount);
        if (amount < 10)
            amountString = "0" + amountString;

        return amount / 100 + "." + amountString.substring(amountString.length() - 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return amount == price.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
