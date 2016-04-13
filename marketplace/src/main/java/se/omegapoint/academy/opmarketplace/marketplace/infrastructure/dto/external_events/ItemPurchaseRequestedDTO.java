package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPurchaseRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseRequestedDTO implements Event, Deserializer<ItemPurchaseRequested> {

    public static final String TYPE = "ItemPurchaseRequested";

    public final String requestId;
    public final String itemId;
    public final int quantity;
    public final String buyerId;

    @JsonCreator
    public ItemPurchaseRequestedDTO(@JsonProperty("requestId") String requestId,
                                    @JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") int quantity,
                                    @JsonProperty("buyerId") String buyerId) {
        this.requestId = notNull(requestId);
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

    @Override
    public ItemPurchaseRequested domainObject() {
        return new ItemPurchaseRequested(new Id(itemId), new Quantity(quantity), new Email(buyerId));
    }
}
