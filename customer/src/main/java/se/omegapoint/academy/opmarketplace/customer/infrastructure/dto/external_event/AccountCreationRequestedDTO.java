package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreationRequestedDTO implements Event, Deserializer<AccountCreationRequested> {

    public static final String TYPE = "AccountCreationRequested";

    public final String requestId;
    public final String email;
    public final UserDTO user;

    @JsonCreator
    public AccountCreationRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") String email,
            @JsonProperty("user") UserDTO user){
        this.requestId = notNull(requestId);
        this.email = notNull(email);
        this.user = notNull(user);
    }


    @Override
    public AccountCreationRequested domainObject() {
        return new AccountCreationRequested(new Email(email), user.domainObject());
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
