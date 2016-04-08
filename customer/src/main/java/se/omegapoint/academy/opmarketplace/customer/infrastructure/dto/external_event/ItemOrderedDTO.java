package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemOrdered;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOrderedDTO implements Event, Deserializer<ItemOrdered> {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final String orderId;
    public final int price;
    public final String sellerId;
    public final String buyerId;

    @JsonCreator
    public ItemOrderedDTO(@JsonProperty("requestId") String requestId,
                          @JsonProperty("orderId") String orderId,
                          @JsonProperty("price") int price,
                          @JsonProperty("sellerId") String sellerId,
                          @JsonProperty("buyerId") String buyerId){
        this.requestId = notNull(requestId);
        this.orderId = orderId;
        this.price = notNull(price);
        this.sellerId = notNull(sellerId);
        this.buyerId = notNull(buyerId);
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
    public ItemOrdered domainObject() {
        return new ItemOrdered(UUID.fromString(orderId), new Email(sellerId), new Credit(price), new Email(buyerId));
    }
}
