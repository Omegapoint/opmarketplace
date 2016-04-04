package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

public class DisableFeatureDTO implements Command {

    public static final String TYPE = "DisableFeature";

    public final int noSeconds;
    public final String eventName;

    public DisableFeatureDTO(int noSeconds, String eventName) {
        this.noSeconds = noSeconds;
        this.eventName = eventName;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
