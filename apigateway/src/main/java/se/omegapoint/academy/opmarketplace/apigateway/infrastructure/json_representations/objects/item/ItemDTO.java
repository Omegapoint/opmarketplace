package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO implements DTO {

    public final String id;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final CreditDTO price;
    public final QuantityDTO supply;
    public final EmailDTO seller;

    @JsonCreator
    public ItemDTO(@JsonProperty("id") String id,
                   @JsonProperty("title") TitleDTO title,
                   @JsonProperty("description") DescriptionDTO description,
                   @JsonProperty("price") CreditDTO price,
                   @JsonProperty("supply") QuantityDTO supply,
                   @JsonProperty("seller") EmailDTO seller) {
        this.id = notNull(id);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }
}
