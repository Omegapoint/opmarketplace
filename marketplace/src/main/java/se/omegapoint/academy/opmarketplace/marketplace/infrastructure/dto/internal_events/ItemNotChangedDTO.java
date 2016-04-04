package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotChanged;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemNotChangedDTO implements Event, Serializer {

    public static final String TYPE = "ItemNotChanged";

    public final String requestId;
    public final String reason;

    public ItemNotChangedDTO(ItemNotChanged itemNotCreated, String requestId) {
        this.requestId = notNull(requestId);
        this.reason = notNull(itemNotCreated).reason();
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
