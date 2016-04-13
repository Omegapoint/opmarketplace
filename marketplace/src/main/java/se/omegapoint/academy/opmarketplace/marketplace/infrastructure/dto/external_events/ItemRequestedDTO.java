package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequestedDTO implements Event, Deserializer<ItemRequested> {

    public static final String TYPE = "ItemRequested";

    public final String requestId;
    public final String itemId;

    @JsonCreator
    public ItemRequestedDTO(@JsonProperty("requestId") String requestId,
                            @JsonProperty("itemId") String itemId) {
        this.requestId = notNull(requestId);
        this.itemId = notNull(itemId);
    }

    @Override
    public ItemRequested domainObject() {
        return new ItemRequested(new Id(itemId));
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
