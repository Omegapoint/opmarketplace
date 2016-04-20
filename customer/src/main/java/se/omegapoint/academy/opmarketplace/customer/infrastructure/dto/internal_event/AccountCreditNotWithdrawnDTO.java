package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditNotDeposited;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditNotWithdrawn;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditNotWithdrawnDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreditNotWithdrawn";

    public final String requestId;
    public final String reason;

    public AccountCreditNotWithdrawnDTO(AccountCreditNotWithdrawn creditNotWithdrawn, String requestId) {
        notNull(creditNotWithdrawn);
        this.requestId = notNull(requestId);
        this.reason = creditNotWithdrawn.reason();
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
