package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class QuantityDTO implements DTO {
    public final int amount;

    @JsonCreator
    public QuantityDTO(@JsonProperty("amount") int amount) {
        this.amount = notNull(amount);
    }
}
