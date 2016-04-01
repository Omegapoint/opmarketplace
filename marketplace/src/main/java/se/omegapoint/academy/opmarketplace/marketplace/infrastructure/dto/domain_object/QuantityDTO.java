package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class QuantityDTO implements DTO, Deserializer<Quantity>{
    public final int amount;

    public QuantityDTO(Quantity quantity){
        this.amount = notNull(quantity).amount();
    }

    @JsonCreator
    public QuantityDTO(@JsonProperty("amount") int amount) {
        this.amount = notNull(amount);
    }

    @Override
    public Quantity domainObject() {
        return new Quantity(this.amount);
    }
}
