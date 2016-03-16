package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountNotCreated;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountNotCreatedModel implements JsonModel {
    public static final String TYPE = "AccountNotCreated";

    private EmailModel email;
    private String reason;

    public AccountNotCreatedModel() {}

    public AccountNotCreatedModel(AccountNotCreated accountNotCreated) {
        notNull(accountNotCreated);
        this.email = new EmailModel(accountNotCreated.email());
        this.reason = accountNotCreated.reason();
    }

    public EmailModel getEmail() {
        return email;
    }

    public String getReason(){
        return reason;
    }

    // TODO: 16/03/16 No use for this method
    @Override
    public <T> T domainObject() {
        return null;
    }
}

