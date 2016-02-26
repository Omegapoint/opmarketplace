package se.omegapoint.accademy.opmarketplace.messageservice.domain.models;

public class RuleCommand {

    private String eventType;
    private boolean allow;

    public RuleCommand() {}

    public String getEventType() {
        return eventType;
    }

    public boolean isAllow() {
        return allow;
    }
}
