package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountDeletionRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionRequestedDTO implements Event, Deserializer<AccountDeletionRequested> {

    public static final String TYPE = "AccountDeletionRequested";

    public final String requestId;
    public final EmailDTO email;

    @JsonCreator
    public AccountDeletionRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("email") EmailDTO email) {
        this.requestId = notNull(requestId);
        this.email = notNull(email);
    }

    @Override
    public AccountDeletionRequested domainObject() {
        return new AccountDeletionRequested(email.domainObject());
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