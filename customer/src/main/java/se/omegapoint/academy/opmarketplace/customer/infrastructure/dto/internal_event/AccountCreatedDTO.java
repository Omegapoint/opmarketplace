package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreated";

    private String requestId;
    private EmailDTO email;
    private UserDTO user;

    public AccountCreatedDTO(){}

    public AccountCreatedDTO(AccountCreated accountCreated, String requestId) {
        notNull(accountCreated);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(accountCreated.email());
        this.user = new UserDTO(accountCreated.user());
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
