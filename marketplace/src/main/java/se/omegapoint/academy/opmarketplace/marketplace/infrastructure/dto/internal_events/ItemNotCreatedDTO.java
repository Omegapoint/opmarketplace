package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotCreated;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class ItemNotCreatedDTO implements Event, Serializer{

    public static final String TYPE = "ItemNotCreated";

    public final String requestId;
    public final String reason;

    public ItemNotCreatedDTO(ItemNotCreated itemNotCreated, String requestId) {
        notNull(itemNotCreated);
        this.requestId = notNull(requestId);
        this.reason = itemNotCreated.reason();
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

