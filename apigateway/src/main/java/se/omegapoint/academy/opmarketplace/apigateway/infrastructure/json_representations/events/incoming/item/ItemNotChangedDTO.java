package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemNotChangedDTO implements Event {

    public static final String TYPE = "ItemNotChanged";

    public final String requestId;
    public final String reason;

    @JsonCreator
    public ItemNotChangedDTO(@JsonProperty("requestId") String requestId,
                             @JsonProperty("reason") String reason) {
        this.requestId = notNull(requestId);
        this.reason = notNull(reason);
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
