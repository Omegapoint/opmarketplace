package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemChangeRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemChangeRequestedDTO implements Event, Deserializer<ItemChangeRequested> {

    public static final String TYPE = "ItemChangeRequested";
    public final String requestId;
    public final String id;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;

    @JsonCreator
    public ItemChangeRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("id") String itemId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("price") int price,
            @JsonProperty("supply") int supply) {
        this.requestId = notBlank(requestId);
        this.id = notBlank(itemId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
    }

    @Override
    public ItemChangeRequested domainObject() {
        return new ItemChangeRequested(
                UUID.fromString(id),
                new Title(title),
                new Description(description),
                new Credit(price),
                new Quantity(supply));
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
