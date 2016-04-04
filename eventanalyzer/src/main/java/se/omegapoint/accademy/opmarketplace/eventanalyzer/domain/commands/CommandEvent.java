package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

public final class CommandEvent {
    private String eventType;
    private boolean allow;

    public CommandEvent(String eventType, boolean allow) {
        this.eventType = eventType;
        this.allow = allow;
    }

    public CommandEvent() {}

    public String getEventType() {
        return eventType;
    }

    public boolean isAllow() {
        return allow;
    }
}
