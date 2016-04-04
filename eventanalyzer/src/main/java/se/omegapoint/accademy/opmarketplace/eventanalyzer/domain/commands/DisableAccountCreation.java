package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

public class DisableAccountCreation {
    public final int noSeconds;

    public DisableAccountCreation(int noSeconds) {
        this.noSeconds = noSeconds;
    }
}
