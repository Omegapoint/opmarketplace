package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditDepositRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditWithdrawalRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditWithdrawalRequestedDTO implements Event, Deserializer<AccountCreditWithdrawalRequested> {

    public static final String TYPE = "AccountCreditWithdrawalRequested";

    public final String requestId;
    public final String email;
    public final int credit;

    @JsonCreator
    public AccountCreditWithdrawalRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") String email,
            @JsonProperty("credit") int credits) {
        this.requestId = notNull(requestId);
        this.email = notNull(email);
        this.credit = notNull(credits);
    }

    @Override
    public AccountCreditWithdrawalRequested domainObject() {
        return new AccountCreditWithdrawalRequested(new Email(email), new Credit(credit));
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
