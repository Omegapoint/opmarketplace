package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemNotOrderedDTO implements Event, Serializer {

    public static final String TYPE = "ItemNotOrdered";

    public final String requestId;
    public final String reason;

    public ItemNotOrderedDTO(ItemNotOrdered itemNotOrdered, String requestId) {
        this.requestId = notNull(requestId);
        this.reason = notNull(itemNotOrdered).reason();
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
