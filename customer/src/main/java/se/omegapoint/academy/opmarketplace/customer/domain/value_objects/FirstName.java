package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;

import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.isFalse;
import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class FirstName {
    private final int NAME_LENGTH = 25;
    private final String ILLEGAL_FORMAT = "First name can only contain letters and '-'";
    private final String ILLEGAL_LENGTH = "First name cannot be longer than " + NAME_LENGTH + " characters.";

    private final String name;

    public FirstName(String name) {
        notBlank(name);
        String trimmedName = name.trim();
        isTrue(trimmedName.length() <= NAME_LENGTH, ILLEGAL_LENGTH);
        isTrue(trimmedName.matches("[\\w\\-]+"), ILLEGAL_FORMAT);
        isFalse(trimmedName.contains("_"), ILLEGAL_FORMAT);
        this.name = trimmedName;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName = (FirstName) o;
        return Objects.equals(name, firstName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
