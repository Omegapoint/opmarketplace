package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreditNotDeposited;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditNotDepositedDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreditNotDeposited";

    public final String requestId;
    public final String reason;

    public AccountCreditNotDepositedDTO(AccountCreditNotDeposited creditNotDeposited, String requestId) {
        notNull(creditNotDeposited);
        this.requestId = notNull(requestId);
        this.reason = creditNotDeposited.reason();
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
