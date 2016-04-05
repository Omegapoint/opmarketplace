package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO implements DTO, Serializer, Deserializer<Item> {

    public final String id;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final CreditDTO price;
    public final QuantityDTO supply;

    public ItemDTO(Item item){
        notNull(item);
        this.id = item.id().toString();
        this.title = new TitleDTO(item.title());
        this.description = new DescriptionDTO(item.description());
        this.price = new CreditDTO(item.price());
        this.supply = new QuantityDTO(item.supply());
    }

    @JsonCreator
    public ItemDTO(@JsonProperty("id") String id,
                   @JsonProperty("title") TitleDTO title,
                   @JsonProperty("description") DescriptionDTO description,
                   @JsonProperty("price") CreditDTO price,
                   @JsonProperty("supply") QuantityDTO supply){
        this.id = notNull(id);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
    }

    @Override
    public Item domainObject() {
        return new Item(UUID.fromString(id),
                title.domainObject(),
                description.domainObject(),
                price.domainObject(),
                supply.domainObject());
    }
}
