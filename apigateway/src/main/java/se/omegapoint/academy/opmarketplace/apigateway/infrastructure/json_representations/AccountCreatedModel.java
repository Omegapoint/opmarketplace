package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreatedModel implements JsonModel {

    public static final String TYPE = "AccountCreated";

    private EmailModel email;

    public AccountCreatedModel() {}

    public EmailModel getEmail() {
        return email;
    }

}
