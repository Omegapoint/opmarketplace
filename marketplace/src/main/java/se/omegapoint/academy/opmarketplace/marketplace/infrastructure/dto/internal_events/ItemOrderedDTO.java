package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;


import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrderedDTO implements Event, Serializer {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final String orderId;
    public final int quantity;
    public final int price;
    public final String sellerId;
    public final String buyerId;

    public ItemOrderedDTO(ItemOrdered itemOrdered, String requestId){
        this.requestId = notNull(requestId);
        notNull(itemOrdered);
        this.orderId = itemOrdered.orderId().toString();
        this.quantity = itemOrdered.quantity().amount();
        this.price = itemOrdered.price().amount();
        this.sellerId = itemOrdered.sellerId().address();
        this.buyerId = itemOrdered.buyerId().address();
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
