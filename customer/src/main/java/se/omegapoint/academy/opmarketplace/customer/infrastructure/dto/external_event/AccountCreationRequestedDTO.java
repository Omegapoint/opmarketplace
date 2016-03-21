package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreationRequestedDTO implements Event, Deserializer<AccountCreationRequested> {

    public static final String TYPE = "AccountCreationRequested";

    public final String requestId;
    public final EmailDTO email;
    public final UserDTO user;

    @JsonCreator
    public AccountCreationRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") EmailDTO email,
            @JsonProperty("user") UserDTO user){
        this.requestId = notNull(requestId);
        this.email = notNull(email);
        this.user = notNull(user);
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
