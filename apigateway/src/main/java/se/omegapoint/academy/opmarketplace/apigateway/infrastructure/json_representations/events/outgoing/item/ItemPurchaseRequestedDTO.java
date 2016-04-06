package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account_item.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.QuantityDTO;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseRequestedDTO implements Event {

    public static final String TYPE = "ItemPurchaseRequested";

    public final String requestId;
    public final String itemId;
    public final QuantityDTO quantity;
    public final EmailDTO buyerId;

    @JsonCreator
    public ItemPurchaseRequestedDTO(@JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") QuantityDTO quantity,
                                    @JsonProperty("buyerId") EmailDTO buyerId) {
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
