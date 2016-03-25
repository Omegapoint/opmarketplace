package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.ItemDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemCreatedDTO implements Event, Serializer {

    public static final String TYPE = "ItemCreated";

    public final String requestId;
    public final ItemDTO item;

    public ItemCreatedDTO(ItemCreated event, String requestId) {
        this.requestId = requestId;
        this.item = new ItemDTO(notNull(event).item());
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
