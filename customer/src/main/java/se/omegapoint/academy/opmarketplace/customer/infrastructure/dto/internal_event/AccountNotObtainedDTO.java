package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtainedDTO implements Event, Serializer {

    public static final String TYPE = "AccountNotObtained";

    public final String requestId;
    public final String reason;

    public AccountNotObtainedDTO(AccountNotObtained event, String requestId) {
        notNull(event);
        this.requestId = notNull(requestId);
        this.reason = event.reason();
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
