package se.omegapoint.accademy.opmarketplace.messageservice.domain.models;

public class CommandEvent {

    private String eventType;
    private boolean allow;

    public CommandEvent() {}

    public String getEventType() {
        return eventType;
    }

    public boolean isAllow() {
        return allow;
    }
}
