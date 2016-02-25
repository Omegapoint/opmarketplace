package se.omegapoint.accademy.opmarketplace.messageservice.models;


public final class Channels {
    public static final String RULE_COMMAND = "rule_command";
    public static final String EVENTS = "events";
    public static final String ALL_CHANNELS_REGEX = "\\w"; //TODO: Includes RULE_COMMAND channel atm.

    private Channels() {}
}
