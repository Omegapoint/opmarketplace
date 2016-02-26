package se.omegapoint.accademy.opmarketplace.messageservice.domain.models;

public class RuleCommand {

    private final String eventType;
    private final boolean allow;

    public RuleCommand(String eventType, boolean allow) {
        this.eventType = eventType;
        this.allow = allow;
    }

    public String eventType() {
        return eventType;
    }

    public boolean allow() {
        return allow;
    }
}
