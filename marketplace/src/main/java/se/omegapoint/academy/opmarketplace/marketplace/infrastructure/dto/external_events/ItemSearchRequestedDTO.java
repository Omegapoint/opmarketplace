package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemSearchRequested;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemSearchRequestedDTO implements Event, Deserializer<ItemSearchRequested> {
    public static final String TYPE = "ItemSearchRequested";

    public final String requestId;
    public final String query;

    @JsonCreator
    public ItemSearchRequestedDTO(@JsonProperty("requestId") String requestId,
                                  @JsonProperty("query") String query) {
        this.requestId = notNull(requestId);
        this.query = notNull(query);
    }

    @Override
    public ItemSearchRequested domainObject() {
        return new ItemSearchRequested(this.query);
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
