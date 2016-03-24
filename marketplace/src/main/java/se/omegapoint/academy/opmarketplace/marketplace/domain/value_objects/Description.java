package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class Description {

    protected final static int MAX_LENGTH = 5000;

    protected final static String ILLEGAL_LENGTH = "Description cannot be longer than " + MAX_LENGTH + " characters.";
    protected final static String ILLEGAL_CHARACTERS = "Description can only contain letters, digits and '.,%&@/'";

    private final String text;

    public Description(String text) {
        notBlank(text);
        isTrue(text.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
        isTrue(text.matches("[\\w\\.,?%&@/ ]+"), ILLEGAL_CHARACTERS);
        this.text = text;
    }

    public String text(){
        return text;
    }
}
