package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ValidateUsersDTO {

    public static final String TYPE = "ValidateUsers";

    public final int noSeconds;
    public final List<String> users;
    public final boolean onlyImportantUsers;

    @JsonCreator
    public ValidateUsersDTO(@JsonProperty("noSeconds") int noSeconds,
                            @JsonProperty("users") List<String> users,
                            @JsonProperty("onlyImportantUsers") boolean onlyImportantUsers) {
        this.noSeconds = notNull(noSeconds);
        this.users = notNull(users);
        this.onlyImportantUsers = onlyImportantUsers;
    }
}
