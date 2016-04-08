package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountNotCreatedDTO implements Event, Serializer {

    public static final String TYPE = "AccountNotCreated";

    public final String requestId;
    public final String reason;

    public AccountNotCreatedDTO(AccountNotCreated accountNotCreated, String requestId) {
        notNull(accountNotCreated);
        this.requestId = notNull(requestId);
        this.reason = accountNotCreated.reason();
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

