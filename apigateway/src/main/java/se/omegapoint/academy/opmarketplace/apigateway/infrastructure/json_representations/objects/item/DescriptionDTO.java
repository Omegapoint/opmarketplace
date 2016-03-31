package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DescriptionDTO implements DTO {

    public final String text;

    @JsonCreator
    public DescriptionDTO(@JsonProperty("text") String text) {
        this.text = notNull(text);
    }
}