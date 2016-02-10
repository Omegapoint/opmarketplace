package se.omegapoint.academy.domain.items;

import java.math.BigDecimal;

public final class Price {

    protected static final int EXPONENT_LIMIT = 9;
    protected static final int DECIMAL_POINT_LIMIT = 4;

    public static final String ILLEGAL_FORMAT = "Price can only contain digits and a '.'";
    public static final String ILLEGAL_SIZE = "Price has to be less than " + String.valueOf(Math.round(Math.pow(10, Price.EXPONENT_LIMIT)));
    public static final String ILLEGAL_PRECISION = "Price cannot have more than " + DECIMAL_POINT_LIMIT + " decimal places.";

    private final BigDecimal amount;

    public Price(final String amount) {
        if (!amount.matches("\\d+(\\.\\d+)*"))
            throw new IllegalArgumentException(ILLEGAL_FORMAT);
        if ((amount.contains(".") && amount.indexOf('.') > EXPONENT_LIMIT) || amount.length() > EXPONENT_LIMIT)
            throw new IllegalArgumentException(ILLEGAL_SIZE);
        if (amount.contains(".") && amount.length() - amount.indexOf('.') >= DECIMAL_POINT_LIMIT)
            throw new IllegalArgumentException(ILLEGAL_PRECISION);
        this.amount = new BigDecimal(amount);
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
