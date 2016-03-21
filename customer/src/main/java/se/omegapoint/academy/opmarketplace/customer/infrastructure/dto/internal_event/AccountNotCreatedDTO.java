package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountNotCreatedDTO implements Event, Serializer {

    public static final String TYPE = "AccountNotCreated";

    private String requestId;
    private EmailDTO email;
    private String reason;

    public AccountNotCreatedDTO() {}

    public AccountNotCreatedDTO(AccountNotCreated accountNotCreated, String requestId) {
        notNull(accountNotCreated);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(accountNotCreated.email());
        this.reason = accountNotCreated.reason();
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public String getReason(){
        return reason;
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

