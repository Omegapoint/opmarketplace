package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.ItemDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemChangedDTO  implements Event, Serializer {

    public static final String TYPE = "ItemChanged";

    public final String requestId;
    public final ItemDTO item;

    public ItemChangedDTO(ItemChanged event, String requestId) {
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
