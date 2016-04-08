package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletedDTO implements Event, Serializer {

    public static final String TYPE = "AccountDeleted";

    public final String requestId;
    public final String email;

    public AccountDeletedDTO(AccountDeleted accountDeleted, String requestId) {
        notNull(accountDeleted);
        this.requestId = notNull(requestId);
        this.email = accountDeleted.email().address();
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId();
    }
}
