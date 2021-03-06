package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditWithdrawnDTO implements Event {

    public static final String TYPE = "AccountCreditWithdrawn";

    public final String requestId;
    public final int credit;

    @JsonCreator
    public AccountCreditWithdrawnDTO(@JsonProperty("requestId") String requestId,
                                     @JsonProperty("credit") int credit) {
        this.requestId = notNull(requestId);
        this.credit = notNull(credit);
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
