package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCreationRequestedDTO implements Event, Deserializer<ItemCreationRequested>{

    public static final String TYPE = "ItemCreationRequested";
    public final String requestId;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;
    public final String seller;

    @JsonCreator
    public ItemCreationRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("price") int price,
            @JsonProperty("supply") int supply,
            @JsonProperty("seller") String seller) {
        this.requestId = notNull(requestId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }

    @Override
    public ItemCreationRequested domainObject() {
        return new ItemCreationRequested(
                new Title(title),
                new Description(description),
                new Credit(price),
                new Quantity(supply),
                new Email(seller));
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
