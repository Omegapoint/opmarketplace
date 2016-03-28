package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class IncomingRemoteEvent {

    public final String type;
    public final String data;

    public IncomingRemoteEvent(String type, String data) {
        this.type = notNull(type);
        this.data = notNull(data);
    }
}
