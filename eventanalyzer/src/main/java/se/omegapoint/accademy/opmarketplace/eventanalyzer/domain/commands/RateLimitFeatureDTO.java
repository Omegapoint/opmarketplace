package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

public class RateLimitFeatureDTO implements Command {

    public static final String TYPE = "RateLimitFeature";

    public final int interval;
    public final int noSeconds;

    public RateLimitFeatureDTO(int interval, int noSeconds) {
        this.interval = interval;
        this.noSeconds = noSeconds;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
