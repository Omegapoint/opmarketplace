package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;

import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.Objects;
import java.util.UUID;

public class Id {

    private UUID value;

    public Id(String id){
        try {
            this.value = UUID.fromString(id);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentValidationException("Illegal Format: Id does not conform to UUID.");
        }
    }

    public Id(){
        this.value = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
