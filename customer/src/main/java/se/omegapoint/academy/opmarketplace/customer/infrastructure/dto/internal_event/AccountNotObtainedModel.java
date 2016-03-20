package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtainedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountNotObtained";

    private String requestId;
    private EmailModel email;
    private String reason;

    public AccountNotObtainedModel() {}

    public AccountNotObtainedModel(AccountNotObtained event, String requestId) {
        notNull(event);
        this.requestId = notNull(requestId);
        this.email = new EmailModel(event.email());
        this.reason = event.reason();
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailModel getEmail() {
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
