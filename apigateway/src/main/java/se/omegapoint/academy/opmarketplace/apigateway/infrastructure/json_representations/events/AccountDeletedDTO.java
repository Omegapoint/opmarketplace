package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDeletedDTO implements Event {
    public static final String TYPE = "AccountDeleted";

    public final EmailDTO email;

    public AccountDeletedDTO(@JsonProperty("email") EmailDTO email) {
        this.email = notNull(email);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
