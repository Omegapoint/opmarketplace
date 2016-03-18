package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserDTO;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreationRequestedDTO implements Event {
    public static final String TYPE = "AccountCreationRequested";

    public final String requestId;
    public final EmailDTO email;
    public final UserDTO user;

    @JsonCreator
    public AccountCreationRequestedDTO(@JsonProperty("email") EmailDTO email, @JsonProperty("user") UserDTO user) {
        this.requestId = randomString();
        this.email = notNull(email);
        this.user = notNull(user);
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
