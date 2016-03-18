package se.omegapoint.academy.opmarketplace.customer.application;

import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Optional;

public class Validator {
    public static Optional<String> validate(Deserializer deserializer) {
        try {
            deserializer.domainObject();
            return Optional.empty();
        } catch (IllegalArgumentValidationException e) {
            e.printStackTrace();
            return Optional.of(e.getMessage());
        }
    }
}
