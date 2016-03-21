package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotDeletedDTO implements Event, Serializer {

    public static final String TYPE = "AccountNotDeleted";

    public final String requestId;
    public final String reason;

    public AccountNotDeletedDTO(AccountNotDeleted event, String requestId) {
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
