package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangedDTO implements Event, Serializer {

    public static final String TYPE = "AccountUserChanged";

    private String requestId;
    private EmailDTO email;
    private UserDTO user;

    public AccountUserChangedDTO(){}

    public AccountUserChangedDTO(AccountUserChanged accountUserChanged, String requestId) {
        notNull(accountUserChanged);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(accountUserChanged.email());
        this.user = new UserDTO(accountUserChanged.user());
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public UserDTO getUser() {
        return user;
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
