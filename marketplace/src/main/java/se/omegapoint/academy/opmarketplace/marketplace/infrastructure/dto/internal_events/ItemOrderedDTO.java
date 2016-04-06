package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.CreditDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.QuantityDTO;


import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemOrderedDTO implements Event, Serializer {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final QuantityDTO quantity;
    public final CreditDTO price;
    public final EmailDTO sellerId;
    public final EmailDTO buyerId;

    public ItemOrderedDTO(ItemOrdered itemOrdered, String requestId){
        this.requestId = notNull(requestId);
        this.quantity = new QuantityDTO(notNull(itemOrdered).quantity().amount());
        this.price = new CreditDTO(itemOrdered.price().amount());
        this.sellerId = new EmailDTO(itemOrdered.sellerId().address());
        this.buyerId = new EmailDTO(itemOrdered.buyerId().address());
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
