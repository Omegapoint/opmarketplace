package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReservationRequestedDTO implements Event {

    public static final String TYPE = "ItemReservationRequested";

    public final String requestId;
    public final String itemId;
    public final int quantity;
    public final String reserverId;

    @JsonCreator
    public ItemReservationRequestedDTO(@JsonProperty("itemId") String itemId,
                                    @JsonProperty("quantity") int quantity,
                                    @JsonProperty("reserverId") String reserverId) {
        this.requestId = randomString();
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
}
