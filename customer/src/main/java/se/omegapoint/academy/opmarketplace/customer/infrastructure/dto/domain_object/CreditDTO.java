package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDTO implements DTO, Serializer, Deserializer<Credit> {

    public final int amount;

    public CreditDTO(Credit credit){
        this.amount = notNull(credit).amount();
    }

    @JsonCreator
    public CreditDTO(@JsonProperty("amount") int amount) {
        this.amount = notNull(amount);
    }

    @Override
    public Credit domainObject() {
        return new Credit(this.amount);
    }
}
