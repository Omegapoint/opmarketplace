package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO implements DTO {

    public final String id;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final PriceDTO price;

    @JsonCreator
    public ItemDTO(@JsonProperty("id") String id,
                   @JsonProperty("title") TitleDTO title,
                   @JsonProperty("description") DescriptionDTO description,
                   @JsonProperty("price") PriceDTO price) {
        this.id = notNull(id);
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
