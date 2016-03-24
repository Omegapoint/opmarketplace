package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionDTO implements DTO, Serializer, Deserializer<Description> {

    public final String text;

    public DescriptionDTO(Description description){
        this.text = notNull(description).text();
    }

    @JsonCreator
    public DescriptionDTO(@JsonProperty("text") String text) {
        this.text = notNull(text);
    }

    @Override
    public Description domainObject() {
        return new Description(this.text);
    }
}
