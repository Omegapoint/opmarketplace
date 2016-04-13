package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Order;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import java.util.UUID;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO implements DTO, Serializer, Deserializer<Order> {

    public final String id;
    public final int sum;
    public final String sellerId;
    public final String buyerId;

    public OrderDTO(Order order){
        notNull(order);
        this.id = order.id().toString();
        this.sum = order.sum().amount();
        this.sellerId = order.sellerId().address();
        this.buyerId = order.buyerId().address();
    }
    @JsonCreator
    public OrderDTO(@JsonProperty("id") String id,
                    @JsonProperty("sum") int sum,
                    @JsonProperty("sellerId") String sellerId,
                    @JsonProperty("buyerId") String buyerId){
        this.id = notNull(id);
        this.sum = notNull(sum);
        this.sellerId = notNull(sellerId);
        this.buyerId = notNull(buyerId);
    }

    @Override
    public Order domainObject() {
        return new Order(
                new Id(id),
                new Email(sellerId),
                new Credit(sum),
                new Email(buyerId));
    }
}
