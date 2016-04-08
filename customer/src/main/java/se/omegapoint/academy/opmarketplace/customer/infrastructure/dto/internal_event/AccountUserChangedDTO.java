package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangedDTO implements Event, Serializer {

    public static final String TYPE = "AccountUserChanged";

    public final String requestId;
    public final String email;
    public final UserDTO user;

    public AccountUserChangedDTO(AccountUserChanged accountUserChanged, String requestId) {
        notNull(accountUserChanged);
        this.requestId = notNull(requestId);
        this.email = accountUserChanged.email().address();
        this.user = new UserDTO(accountUserChanged.user());
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
