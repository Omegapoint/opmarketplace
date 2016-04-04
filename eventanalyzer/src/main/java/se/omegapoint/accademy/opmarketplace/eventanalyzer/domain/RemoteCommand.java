package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.Command;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class RemoteCommand {

    public final String type;
    public final Command data;

    public RemoteCommand(Command data) {
        this.data = notNull(data);
        this.type = notNull(data.type());
    }
}
