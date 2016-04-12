package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RateLimitFeatureDTO {

    public static final String TYPE = "RateLimitFeature";

    public final int interval;
    public final int noSeconds;

    @JsonCreator
    public RateLimitFeatureDTO(@JsonProperty("interval") int interval, @JsonProperty("noSeconds") int noSeconds) {
        this.interval = interval;
        this.noSeconds = noSeconds;
    }
}
