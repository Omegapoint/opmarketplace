package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotReserved;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemNotReservedDTO implements Event, Serializer {

    public static final String TYPE = "ItemNotReserved";

    public final String requestId;
    public final String reason;

    public ItemNotReservedDTO(ItemNotReserved event, String requestId) {
        this.requestId = notNull(requestId);
        this.reason = notNull(event).reason();
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
