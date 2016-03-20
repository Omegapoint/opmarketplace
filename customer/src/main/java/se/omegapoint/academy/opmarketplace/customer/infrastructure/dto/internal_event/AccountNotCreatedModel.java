package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountNotCreatedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountNotCreated";

    private String requestId;
    private EmailModel email;
    private String reason;

    public AccountNotCreatedModel() {}

    public AccountNotCreatedModel(AccountNotCreated accountNotCreated, String requestId) {
        notNull(accountNotCreated);
        this.requestId = notNull(requestId);
        this.email = new EmailModel(accountNotCreated.email());
        this.reason = accountNotCreated.reason();
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailModel getEmail() {
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

