package se.omegapoint.academy.domain.items;

import java.math.BigDecimal;

public final class Price {

    public static final String ILLEGAL_FORMAT = "Argument can only contain digits and a '.'";

    private final BigDecimal amount;

    public Price(final String amount) {
        if (amount.matches("\\d+(\\.\\d+)*"))
            this.amount = new BigDecimal(amount);
        else
            throw new  IllegalArgumentException(ILLEGAL_FORMAT);
    }

    public String amount(){
        return amount.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        return amount != null ? amount.equals(price.amount) : price.amount == null;

    }

    @Override
    public int hashCode() {
        return amount != null ? amount.hashCode() : 0;
    }
}
