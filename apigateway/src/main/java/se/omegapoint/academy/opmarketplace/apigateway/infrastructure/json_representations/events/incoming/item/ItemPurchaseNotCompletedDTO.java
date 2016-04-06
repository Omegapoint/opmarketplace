package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseNotCompletedDTO implements Event {

    public static final String TYPE = "ItemPurchaseNotCompleted";

    @JsonIgnore
    public final String requestId;
    public final String reason;

    @JsonCreator
    public ItemPurchaseNotCompletedDTO(@JsonProperty("requestId") String requestId,
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
