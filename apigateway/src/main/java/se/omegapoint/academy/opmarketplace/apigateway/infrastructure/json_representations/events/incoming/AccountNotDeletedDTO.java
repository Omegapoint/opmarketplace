package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountNotDeletedDTO implements Event{
    public static final String TYPE = "AccountNotDeleted";

    @JsonIgnore
    public final EmailDTO email;
    public final String reason;

    @JsonCreator
    public AccountNotDeletedDTO(@JsonProperty("email") EmailDTO email, @JsonProperty("reason") String reason) {
        this.email = notNull(email);
        this.reason = notNull(reason);
    }

    @Override
    public String type() {
        return TYPE;
    }
}
