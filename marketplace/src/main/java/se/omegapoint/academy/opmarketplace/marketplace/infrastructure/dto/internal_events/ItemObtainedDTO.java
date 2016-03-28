package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.ItemDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemObtainedDTO implements Event, Serializer {

    public static final String TYPE = "ItemObtained";
    public final String requestId;
    public final ItemDTO item;

    public ItemObtainedDTO(ItemObtained event, String requestId){
        this.requestId = notNull(requestId);
        this.item = new ItemDTO(event.item());
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
