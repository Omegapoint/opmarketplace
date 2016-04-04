package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements DTO {

    public final String firstName;
    public final String lastName;

    public UserDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }
}
