package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemPaymentCompleted;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.CreditDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemPaymentCompletedDTO implements Event, Serializer {

    public static final String TYPE = "ItemPaymentCompleted";

    public final String requestId;
    public final EmailDTO sellerId;
    public final CreditDTO price;
    public final EmailDTO buyerId;

    public ItemPaymentCompletedDTO(ItemPaymentCompleted itemPaymentCompleted, String requestId) {
        this.requestId = notNull(requestId);
        this.sellerId = new EmailDTO(itemPaymentCompleted.sellerId().address());
        this.price = new CreditDTO(itemPaymentCompleted.price().amount());
        this.buyerId = new EmailDTO(itemPaymentCompleted.buyerId().address());
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
