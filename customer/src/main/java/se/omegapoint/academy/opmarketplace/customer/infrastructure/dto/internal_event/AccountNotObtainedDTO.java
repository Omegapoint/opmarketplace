package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtainedDTO implements Event, Serializer {

    public static final String TYPE = "AccountNotObtained";

    private String requestId;
    private EmailDTO email;
    private String reason;

    public AccountNotObtainedDTO() {}

    public AccountNotObtainedDTO(AccountNotObtained event, String requestId) {
        notNull(event);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(event.email());
        this.reason = event.reason();
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public String getReason() {
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
