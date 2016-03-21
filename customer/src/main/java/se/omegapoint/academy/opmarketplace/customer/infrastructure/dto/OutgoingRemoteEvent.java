package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class OutgoingRemoteEvent {

    public final String type;
    public final Event data;

    // TODO: 08/03/16 Fix factory method.

    public OutgoingRemoteEvent(Event data) {
        notNull(data);
        this.type = notNull(data.type());
        this.data = data;
    }
}
