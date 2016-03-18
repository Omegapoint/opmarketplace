package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class IncomingRemoteEvent {

    public final String type;
    public final String data;

    public IncomingRemoteEvent(String type, String data) {
        this.type = notNull(type);
        this.data = notNull(data);
    }
}
