package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserNotChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserNotChangedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountUserNotChanged";

    private EmailModel email;
    private String reason;

    public AccountUserNotChangedModel() {}

    public AccountUserNotChangedModel(AccountUserNotChanged event) {
        notNull(event);
        this.email = new EmailModel(event.email());
        this.reason = event.reason();
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
}
