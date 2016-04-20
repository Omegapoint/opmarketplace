package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditWithdrawalRequestedDTO implements Event {

    public static final String TYPE = "AccountCreditWithdrawalRequested";

    public final String requestId;
    public final String email;
    public final int credit;

    @JsonCreator
    public AccountCreditWithdrawalRequestedDTO(
            @JsonProperty("email") String email,
            @JsonProperty("credit") int credits) {
        this.requestId = randomString();
        this.email = notNull(email);
        this.credit = notNull(credits);
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
