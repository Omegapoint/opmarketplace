package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotObtained;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtainedModel implements JsonModel {

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
