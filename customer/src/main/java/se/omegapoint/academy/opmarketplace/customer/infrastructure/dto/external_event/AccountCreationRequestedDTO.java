package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

public class AccountCreationRequestedDTO implements Event, Deserializer<AccountCreationRequested> {

    public static final String TYPE = "AccountCreationRequested";

    private String requestId;
    private EmailDTO email;
    private UserDTO user;

    public AccountCreationRequestedDTO(){}

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
    public AccountCreationRequested domainObject() {
        return new AccountCreationRequested(email.domainObject(), user.domainObject());
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
