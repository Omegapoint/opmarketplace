package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemReserved;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReservedDTO implements Event, Serializer {

    public static final String TYPE = "ItemReserved";

    public final String requestId;

    public ItemReservedDTO(ItemReserved event, String requestId) {
        notNull(event);
        this.requestId = notNull(requestId);
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
