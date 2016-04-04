package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class IncomingRemoteEvent {

    public final String type;

    public IncomingRemoteEvent(@JsonProperty("type") String type) {
        this.type = notNull(type);
    }
}
