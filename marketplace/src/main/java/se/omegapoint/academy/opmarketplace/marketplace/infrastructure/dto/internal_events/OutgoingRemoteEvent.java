package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;

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
