package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequestedModel implements JsonModel {

    public static final String TYPE = "AccountRequested";

    private EmailModel email;

    public AccountRequestedModel() {}

    public AccountRequestedModel(EmailModel email) {
        this.email = notNull(email);
    }

    public EmailModel getEmail() {
        return email;
    }
}
