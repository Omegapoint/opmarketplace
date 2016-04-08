package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountDeletionRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDeletionRequestedDTO implements Event, Deserializer<AccountDeletionRequested> {

    public static final String TYPE = "AccountDeletionRequested";

    public final String requestId;
    public final String email;

    @JsonCreator
    public AccountDeletionRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") String email) {
        this.requestId = notNull(requestId);
        this.email = notNull(email);
    }

    @Override
    public AccountDeletionRequested domainObject() {
        return new AccountDeletionRequested(new Email(email));
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
