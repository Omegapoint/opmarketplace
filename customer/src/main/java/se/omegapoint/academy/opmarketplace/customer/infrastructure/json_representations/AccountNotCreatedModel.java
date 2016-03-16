package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountNotCreatedModel implements JsonModel {
    public static final String TYPE = "AccountNotCreated";

    private EmailModel email;
    private String reason;

    public AccountNotCreatedModel() {}

    public AccountNotCreatedModel(EmailModel email, String reason) {
        this.email = notNull(email);
        this.reason = notNull(reason);
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

