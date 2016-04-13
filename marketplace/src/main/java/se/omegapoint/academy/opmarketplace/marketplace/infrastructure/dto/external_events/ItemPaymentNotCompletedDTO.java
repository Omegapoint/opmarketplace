package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPaymentNotCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPaymentNotCompletedDTO implements Event, Deserializer<ItemPaymentNotCompleted> {

    public static final String TYPE = "ItemPaymentNotCompleted";

    public final String requestId;
    public final String orderId;

    public ItemPaymentNotCompletedDTO(@JsonProperty("requestId") String requestId,
                                      @JsonProperty("orderId") String orderId) {
        this.requestId = notNull(requestId);
        this.orderId = notNull(orderId);
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }

    @Override
    public ItemPaymentNotCompleted domainObject() {
        return new ItemPaymentNotCompleted(new Id(orderId));
    }
}
