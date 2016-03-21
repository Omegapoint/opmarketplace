package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public final String requestId;
    public final EmailDTO email;
    public final UserDTO user;

    @JsonCreator
    public AccountUserChangeRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") EmailDTO email,
            @JsonProperty("user") UserDTO user){
        this.requestId = notNull(requestId);
        this.email = notNull(email);
        this.user = notNull(user);
    }

    @Override
    public AccountUserChangeRequested domainObject() {
        return new AccountUserChangeRequested(
                // TODO: 21/03/16 change to email.domainObject and user.domainObject
                new Email(email.address),
                new User(user.firstName, user.lastName));
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
