package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditDepositRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.CreditDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditDepositRequestedDTO implements Event, Deserializer<AccountCreditDepositRequested> {

    public static final String TYPE = "AccountCreditDepositRequested";

    public final String requestId;
    public final EmailDTO email;
    public final CreditDTO credit;

    @JsonCreator
    public AccountCreditDepositRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") EmailDTO email,
            @JsonProperty("credit") CreditDTO credits) {
        this.requestId = notNull(requestId);
        this.email = notNull(email);
        this.credit = notNull(credits);
    }

    @Override
    public AccountCreditDepositRequested domainObject() {
        return new AccountCreditDepositRequested(email.domainObject(), credit.domainObject());
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
