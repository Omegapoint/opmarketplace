package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public final class Email {

    private static final int MAX_LENGTH = 254;
    public static final String ILLEGAL_LENGTH = "Email can only be " + MAX_LENGTH + " characters long.";
    public static final String ILLEGAL_FORMAT = "Illegally formatted email.";

    private final String address;

    public Email(String address) {
        notBlank(address);
        address = address.trim();
        isTrue(address.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
        isTrue(EmailValidator.getInstance().isValid(address), ILLEGAL_FORMAT);
        this.address = address;
    }

    public String address(){
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
