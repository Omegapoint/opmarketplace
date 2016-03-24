package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.DescriptionDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.PriceDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.TitleDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCreationRequestedDTO implements Event, Deserializer<ItemCreationRequested>{

    public static final String TYPE = "ItemCreationRequested";
    public final String requestId;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final PriceDTO price;

    @JsonCreator
    public ItemCreationRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("title") TitleDTO title,
            @JsonProperty("description") DescriptionDTO description,
            @JsonProperty("price") PriceDTO price) {
        this.requestId = notNull(requestId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
    }

    @Override
    public ItemCreationRequested domainObject() {
        return new ItemCreationRequested(
                new Title(title.text),
                new Description(description.text),
                new Price(price.amount));
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
