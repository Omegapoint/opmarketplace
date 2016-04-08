package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemOrdered;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.OrderDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOrderedDTO implements Event, Deserializer<ItemOrdered> {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final OrderDTO order;

    @JsonCreator
    public ItemOrderedDTO(@JsonProperty("requestId") String requestId,
                          @JsonProperty("order") OrderDTO order){
        this.requestId = notNull(requestId);
        this.order = notNull(order);
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
        return new ItemOrdered(order.domainObject());
    }
}
