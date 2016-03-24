package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDTO implements DTO, Serializer, Deserializer<Price> {

    public final String amount;

    public PriceDTO(Price price){
        this.amount = notNull(price).amount();
    }

    @JsonCreator
    public PriceDTO(@JsonProperty("amount") String amount) {
        this.amount = notNull(amount);
    }

    @Override
    public Price domainObject() {
        return new Price(this.amount);
    }
}
