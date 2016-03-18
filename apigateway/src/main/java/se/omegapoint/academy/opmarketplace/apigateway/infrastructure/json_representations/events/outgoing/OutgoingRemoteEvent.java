package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutgoingRemoteEvent {

    public final String type;
    public final Event data;

    // TODO: 08/03/16 Fix factory method.

    public OutgoingRemoteEvent(Event data) {
        notNull(data);
        this.type = data.type();
        this.data = data;
    }
}
