package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO implements DTO, Serializer, Deserializer<Item> {

    public final String id;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;
    public final String seller;

    public ItemDTO(Item item){
        notNull(item);
        this.id = item.id().toString();
        this.title = item.title().text();
        this.description = item.description().text();
        this.price = item.price().amount();
        this.supply = item.supply().amount();
        this.seller = item.seller().address();
    }

    @JsonCreator
    public ItemDTO(@JsonProperty("id") String id,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("price") int price,
                   @JsonProperty("supply") int supply,
                   @JsonProperty("seller") String seller){
        this.id = notNull(id);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }

    @Override
    public Item domainObject() {
        return new Item(UUID.fromString(id),
                new Title(title),
                new Description(description),
                new Credit(price),
                new Quantity(supply),
                new Email(seller));
    }
}
