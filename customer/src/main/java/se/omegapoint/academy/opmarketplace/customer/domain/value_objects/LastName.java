package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;

import se.omegapoint.academy.opmarketplace.customer.MainConfiguration;

import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.*;

public class LastName {

    private final int NAME_LENGTH = 25;
    private final String ILLEGAL_FORMAT = "Illegal Format: Last name can only contain letters and '-'";
    private final String ILLEGAL_LENGTH = "Illegal Format: Last name cannot be longer than " + NAME_LENGTH + " characters.";

    private final String name;

    public LastName(String name) {
        notBlank(name);
        String trimmedName = name.trim();
        if (MainConfiguration.VALIDATION) {
            isTrue(trimmedName.length() <= NAME_LENGTH, ILLEGAL_LENGTH);
            isTrue(trimmedName.matches("[\\w\\-]+"), ILLEGAL_FORMAT);
            isFalse(trimmedName.contains("_"), ILLEGAL_FORMAT);
        }
        this.name = trimmedName;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(name, lastName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
