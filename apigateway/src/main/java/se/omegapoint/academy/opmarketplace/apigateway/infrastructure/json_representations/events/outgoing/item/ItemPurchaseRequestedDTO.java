package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseRequestedDTO implements Event {

    public static final String TYPE = "ItemPurchaseRequested";

    public final String requestId;
    public final String itemId;
    public final int quantity;
    public final String buyerId;

    @JsonCreator
    public ItemPurchaseRequestedDTO(@JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") int quantity,
                                    @JsonProperty("buyerId") String buyerId) {
        this.requestId = randomString();
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.buyerId = notNull(buyerId);
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
