package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemChangeRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.DescriptionDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.PriceDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.QuantityDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.TitleDTO;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemChangeRequestedDTO implements Event, Deserializer<ItemChangeRequested> {

    public static final String TYPE = "ItemChangeRequested";
    public final String requestId;
    public final String itemId;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final PriceDTO price;
    public final QuantityDTO supply;

    @JsonCreator
    public ItemChangeRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("itemId") String itemId,
            @JsonProperty("title") TitleDTO title,
            @JsonProperty("description") DescriptionDTO description,
            @JsonProperty("price") PriceDTO price,
            @JsonProperty("supply") QuantityDTO supply) {
        this.requestId = notBlank(requestId);
        this.itemId = notBlank(itemId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
    }

    @Override
    public ItemChangeRequested domainObject() {
        return new ItemChangeRequested(
                UUID.fromString(itemId),
                new Title(title.text),
                new Description(description.text),
                new Price(price.amount),
                new Quantity(supply.amount));
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
