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
    private String buyerId;

    @JsonCreator
    public ItemPurchaseRequestedDTO(@JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") int quantity) {
        this.requestId = randomString();
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
    }

    public boolean setBuyerId(String buyerId) {
        if (this.buyerId == null) {
            this.buyerId = buyerId;
            return true;
        }
        return false;
    }

    public String getBuyerId() {
        return buyerId;
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
