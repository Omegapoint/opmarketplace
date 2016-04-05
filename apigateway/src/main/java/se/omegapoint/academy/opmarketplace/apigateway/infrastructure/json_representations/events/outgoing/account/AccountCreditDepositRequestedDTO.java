package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account_item.CreditDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account_item.EmailDTO;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditDepositRequestedDTO implements Event {

    public static final String TYPE = "AccountCreditDepositRequested";

    public final String requestId;
    public final EmailDTO email;
    public final CreditDTO credit;

    @JsonCreator
    public AccountCreditDepositRequestedDTO(
            @JsonProperty("email") EmailDTO email,
            @JsonProperty("credit") CreditDTO credits) {
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
