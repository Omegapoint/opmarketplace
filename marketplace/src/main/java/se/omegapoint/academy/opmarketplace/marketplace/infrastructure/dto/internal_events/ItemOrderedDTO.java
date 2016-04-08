package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.OrderDTO;


import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrderedDTO implements Event, Serializer {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final OrderDTO order;

    public ItemOrderedDTO(ItemOrdered itemOrdered, String requestId){
        this.requestId = notNull(requestId);
        this.order = new OrderDTO(notNull(itemOrdered).order());

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
