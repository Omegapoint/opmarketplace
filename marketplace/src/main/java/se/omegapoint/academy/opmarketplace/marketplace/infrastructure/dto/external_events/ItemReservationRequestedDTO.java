package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemReservationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReservationRequestedDTO implements Event, Deserializer<ItemReservationRequested> {

    public static final String TYPE = "ItemReservationRequested";

    public final String requestId;
    public final String itemId;
    public final int quantity;
    public final String reserverId;

    @JsonCreator
    public ItemReservationRequestedDTO(@JsonProperty("requestId") String requestId,
                                       @JsonProperty("itemId") String itemId,
                                       @JsonProperty("quantity") int quantity,
                                       @JsonProperty("reserverId") String reserverId) {
        this.requestId = notNull(requestId);
        this.itemId = notNull(itemId);
        this.quantity = notNull(quantity);
        this.reserverId = notNull(reserverId);
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
    public ItemReservationRequested domainObject() {
        return new ItemReservationRequested(new Id(itemId), new Quantity(quantity), new Email(reserverId));
    }
}
