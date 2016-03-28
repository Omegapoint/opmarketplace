package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountObtainedDTO implements Event {

    public static final String TYPE = "AccountObtained";

    public final String requestId;
    public final AccountDTO account;

    public AccountObtainedDTO(@JsonProperty("requestId") String requestId, @JsonProperty("account") AccountDTO account) {
        this.requestId = notNull(requestId);
        this.account = notNull(account);
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
