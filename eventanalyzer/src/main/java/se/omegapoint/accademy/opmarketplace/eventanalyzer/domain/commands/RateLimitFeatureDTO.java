package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

public class RateLimitFeatureDTO implements Command {

    public static final String TYPE = "RateLimitFeature";

    public final int interval;
    public final String eventName;

    public RateLimitFeatureDTO(int interval, String eventName) {
        this.interval = interval;
        this.eventName = eventName;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
