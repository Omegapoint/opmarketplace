package se.omegapoint.academy.domain.items;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public final class Title {

    public static final int MAX_LENGTH = 50;
    public static final String ILLEGAL_LENGTH = "Title can only be " + MAX_LENGTH + " characters long.";
    public static final String ILLEGAL_CHARACTERS = "Title can only contain letters, digits and spaces";

    private final String text;

    public Title(String text) {
        notBlank(text);
        isTrue(text.length() <= MAX_LENGTH, ILLEGAL_LENGTH);
        isTrue(text.matches("(\\w+\\s*\\.*)+"), ILLEGAL_CHARACTERS);
        this.text = text;
    }

    public String text(){
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title title = (Title) o;

        return text != null ? text.equals(title.text) : title.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
