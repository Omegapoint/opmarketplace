package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentNotCompleted;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentNotCompletedDTO implements Event, Serializer {

    public static final String TYPE = "ItemPaymentNotCompleted";

    public final String requestId;
    public final String reason;

    public ItemPaymentNotCompletedDTO(ItemPaymentNotCompleted itemPaymentNotCompleted, String requestId) {
        this.requestId = notNull(requestId);
        this.reason = notNull(itemPaymentNotCompleted).reason();
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
