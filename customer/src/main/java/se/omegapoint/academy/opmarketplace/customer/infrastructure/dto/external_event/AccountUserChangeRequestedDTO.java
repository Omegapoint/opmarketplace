package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChangeRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountUserChangeRequestedDTO implements Event, Deserializer<AccountUserChangeRequested> {

    public static final String TYPE = "AccountUserChangeRequested";

    private String requestId;
    private EmailDTO email;
    private UserDTO user;

    public AccountUserChangeRequestedDTO() {}

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
    public AccountUserChangeRequested domainObject() {
        return new AccountUserChangeRequested(
                new Email(email.getAddress()),
                new User(user.getFirstName(), user.getLastName()));
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
