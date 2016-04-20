package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditWithdrawn;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreditWithdrawnDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreditWithdrawn";

    public final String requestId;
    public final int credit;

    public AccountCreditWithdrawnDTO(AccountCreditWithdrawn accountCreditWithdrawn, String requestId) {
        this.requestId = notNull(requestId);
        this.credit = notNull(accountCreditWithdrawn).credit().amount();
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
