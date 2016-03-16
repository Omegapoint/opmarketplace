package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserNotChanged;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserNotChangedModel implements JsonModel {

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

    // TODO: 16/03/16 Not used
    @Override
    public <T> T domainObject() {
        return null;
    }
}
