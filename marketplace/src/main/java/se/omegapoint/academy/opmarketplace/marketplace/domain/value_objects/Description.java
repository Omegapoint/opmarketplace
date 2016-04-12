package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import se.omegapoint.academy.opmarketplace.marketplace.MainConfiguration;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class Description {

    protected final static int MAX_LENGTH = 5000;

    protected final static String ILLEGAL_LENGTH = "Illegal Format: Description cannot be longer than " + MAX_LENGTH + " characters.";
    protected final static String ILLEGAL_CHARACTERS = "Illegal Format: Description can only contain letters, digits and '.,%&@/'";

    private final String text;

    public Description(String text) {
        String trimmedText = notBlank(text).trim();
        if (MainConfiguration.VALIDATION) {
            isTrue(trimmedText.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
            isTrue(trimmedText.matches("[\\w\\.,?%&@/ ]+"), ILLEGAL_CHARACTERS);
        }
        this.text = trimmedText;
    }

    public String text(){
        return text;
    }
}
