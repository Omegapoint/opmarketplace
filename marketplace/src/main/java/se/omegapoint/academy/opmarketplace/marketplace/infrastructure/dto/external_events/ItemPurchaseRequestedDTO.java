package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPurchaseRequested;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.QuantityDTO;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPurchaseRequestedDTO implements Event, Deserializer<ItemPurchaseRequested> {

    public static final String TYPE = "ItemPurchaseRequested";

    public final String requestId;
    public final String itemId;
    public final QuantityDTO quantity;
    public final EmailDTO buyerId;

    @JsonCreator
    public ItemPurchaseRequestedDTO(@JsonProperty("requestId") String requestId,
                                    @JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") QuantityDTO quantity,
                                    @JsonProperty("buyerId") EmailDTO buyerId) {
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
        return new ItemPurchaseRequested(UUID.fromString(itemId), quantity.domainObject(), buyerId.domainObject());
    }
}
