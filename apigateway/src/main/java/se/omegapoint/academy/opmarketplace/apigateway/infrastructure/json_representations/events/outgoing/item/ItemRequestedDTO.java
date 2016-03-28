package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequestedDTO implements Event {

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
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
