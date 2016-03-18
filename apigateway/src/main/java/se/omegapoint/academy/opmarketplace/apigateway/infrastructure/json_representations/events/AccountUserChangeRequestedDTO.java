package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserDTO;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserChangeRequestedDTO implements JsonModel {

    public static final String TYPE = "AccountUserChangeRequested";

    public final EmailDTO email;
    public final UserDTO user;

    @JsonCreator
    public AccountUserChangeRequestedDTO(@JsonProperty("email") EmailDTO email, @JsonProperty("user") UserDTO user) {
        this.email = notNull(email);
        this.user = notNull(user);
    }
}

