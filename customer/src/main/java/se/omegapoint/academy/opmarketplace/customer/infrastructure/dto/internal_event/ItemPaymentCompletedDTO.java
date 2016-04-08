package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentCompleted;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentCompletedDTO implements Event, Serializer {

    public static final String TYPE = "ItemPaymentCompleted";

    public final String requestId;
    public final String orderId;
    public final String sellerId;
    public final int price;
    public final String buyerId;

    public ItemPaymentCompletedDTO(ItemPaymentCompleted itemPaymentCompleted, String requestId) {
        this.requestId = notNull(requestId);
        notNull(itemPaymentCompleted);
        this.orderId = itemPaymentCompleted.orderId().toString();
        this.sellerId = itemPaymentCompleted.sellerId().address();
        this.price = itemPaymentCompleted.price().amount();
        this.buyerId = itemPaymentCompleted.buyerId().address();
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
