package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class DisableFeatureDTO {

    public static final String TYPE = "DisableFeature";

    public final int noSeconds;
    public final String eventName;

    @JsonCreator
    public DisableFeatureDTO(@JsonProperty("noSeconds") int noSeconds, @JsonProperty("eventName") String eventName) {
        this.noSeconds = notNull(noSeconds);
        this.eventName = notNull(eventName);
    }
}
