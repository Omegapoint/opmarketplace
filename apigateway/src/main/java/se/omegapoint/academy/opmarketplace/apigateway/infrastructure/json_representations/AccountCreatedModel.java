package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreatedModel implements JsonModel {

    public static final String TYPE = "AccountCreated";

    private EmailModel email;

    public AccountCreatedModel() {}

    public AccountCreatedModel(EmailModel email) {
        this.email = notNull(email);
    }

    public EmailModel getEmail() {
        return email;
    }

}
