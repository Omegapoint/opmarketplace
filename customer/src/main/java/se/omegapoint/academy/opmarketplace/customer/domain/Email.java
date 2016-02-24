package se.omegapoint.academy.opmarketplace.customer.domain;

import org.apache.commons.validator.routines.EmailValidator;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class Email {

    protected static final int MAX_LENGTH = 254;
    public static final String ILLEGAL_LENGTH = "Email can only be " + MAX_LENGTH + " characters long.";
    public static final String ILLEGAL_FORMAT = "Illegally formatted email.";

    private final String address;

    public Email(String address) {
        notBlank(address);
        isTrue(address.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
        isTrue(EmailValidator.getInstance().isValid(address), ILLEGAL_FORMAT);
        this.address = address;
    }

    public String address(){
        return address;
    }
}
