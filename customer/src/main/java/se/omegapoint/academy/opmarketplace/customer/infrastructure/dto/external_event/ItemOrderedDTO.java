package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.ItemOrdered;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.CreditDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOrderedDTO implements Event, Deserializer<ItemOrdered> {

    public static final String TYPE = "ItemOrdered";
    public final String requestId;
    public final CreditDTO price;
    public final EmailDTO sellerId;
    public final EmailDTO buyerId;

    @JsonCreator
    public ItemOrderedDTO(@JsonProperty("requestId") String requestId,
                          @JsonProperty("price") CreditDTO price,
                          @JsonProperty("sellerId") EmailDTO sellerId,
                          @JsonProperty("buyerId") EmailDTO buyerId){
        this.requestId = notNull(requestId);
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
        return new ItemOrdered(new Email(sellerId.address), new Credit(price.amount), new Email(buyerId.address));
    }
}
