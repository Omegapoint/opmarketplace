package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {
    public final EmailDTO email;
    public final UserDTO user;

    @JsonCreator
    public AccountDTO(@JsonProperty("email") EmailDTO email, @JsonProperty("user") UserDTO user) {
        this.email = notNull(email);
        this.user = notNull(user);
    }
}
