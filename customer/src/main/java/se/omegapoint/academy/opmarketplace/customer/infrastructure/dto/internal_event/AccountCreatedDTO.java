package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreated";

    public final String requestId;
    public final EmailDTO email;
    public final UserDTO user;

    public AccountCreatedDTO(AccountCreated accountCreated, String requestId) {
        notNull(accountCreated);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(accountCreated.email());
        this.user = new UserDTO(accountCreated.user());
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
