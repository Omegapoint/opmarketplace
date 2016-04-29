package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;


import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ValidateUsersDTO implements Command {

    public static final String TYPE = "ValidateUsers";

    public final int noSeconds;
    public final List<String> users;
    public final boolean onlyImportantUsers;

    public ValidateUsersDTO(int noSeconds, List<String> users, boolean onlyImportantUsers) {
        this.noSeconds = notNull(noSeconds);
        this.users = notNull(users);
        this.onlyImportantUsers = notNull(onlyImportantUsers);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
