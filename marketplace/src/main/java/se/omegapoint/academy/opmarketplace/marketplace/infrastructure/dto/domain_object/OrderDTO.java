package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO implements DTO, Serializer, Deserializer<Order> {

    public final String id;
    private final String itemId;
    public final int quantity;
    public final int sum;
    public final String sellerId;
    public final String buyerId;

    public OrderDTO(Order order){
        notNull(order);
        this.id = order.id().toString();
        this.itemId = order.itemId().toString();
        this.quantity = order.quantity().amount();
        this.sum = order.sum().amount();
        this.sellerId = order.sellerId().address();
        this.buyerId = order.buyerId().address();
    }
    @JsonCreator
    public OrderDTO(@JsonProperty("id") String id,
                    @JsonProperty("itemId") String itemId,
                   @JsonProperty("quantity") int quantity,
                   @JsonProperty("sum") int sum,
                   @JsonProperty("sellerId") String sellerId,
                   @JsonProperty("buyerId") String buyerId){
        this.id = notNull(id);
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.sum = notNull(sum);
        this.sellerId = notNull(sellerId);
        this.buyerId = notNull(buyerId);
    }

    @Override
    public Order domainObject() {
        return new Order(
                new Id(id),
                new Id(itemId),
                new Email(sellerId),
                new Quantity(quantity),
                new Credit(sum),
                new Email(buyerId));
    }
}
