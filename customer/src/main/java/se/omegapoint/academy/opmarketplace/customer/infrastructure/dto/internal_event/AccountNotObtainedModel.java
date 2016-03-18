package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtainedModel implements DTO {

    public static final String TYPE = "AccountNotObtained";

    private EmailModel email;
    private String reason;

    public AccountNotObtainedModel() {}

    public AccountNotObtainedModel(AccountNotObtained event) {
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

    // TODO: 16/03/16 Never used
    @Override
    public <T> T domainObject() {
        return null;
    }
}