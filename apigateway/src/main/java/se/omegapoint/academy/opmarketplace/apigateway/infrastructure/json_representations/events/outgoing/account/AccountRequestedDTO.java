package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequestedDTO implements Event {

    public static final String TYPE = "AccountRequested";

    public final String requestId;
    public final String email;

    @JsonCreator
    public AccountRequestedDTO(@JsonProperty("email") String email) {
        this.requestId = randomString();
        this.email = notNull(email);
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
