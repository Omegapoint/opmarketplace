package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO implements DTO{
    public final String email;
    public final UserDTO user;
    public final int vault;

    @JsonCreator
    public AccountDTO(@JsonProperty("email") String email,
                      @JsonProperty("user") UserDTO user,
                      @JsonProperty("vault") int vault) {
        this.email = notNull(email);
        this.user = notNull(user);
        this.vault = notNull(vault);
    }
}
