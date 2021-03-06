package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO implements DTO {

    public final String id;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;
    public final String seller;

    @JsonCreator
    public ItemDTO(@JsonProperty("id") String id,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("price") int price,
                   @JsonProperty("supply") int supply,
                   @JsonProperty("seller") String seller) {
        this.id = notNull(id);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }
}
