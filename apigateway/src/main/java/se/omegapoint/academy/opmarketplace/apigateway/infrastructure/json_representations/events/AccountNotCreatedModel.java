package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountNotCreatedModel implements JsonModel {
    public static final String TYPE = "AccountNotCreated";

    private EmailModel email;
    private String reason;

    public AccountNotCreatedModel() {}

    public EmailModel getEmail() {
        return email;
    }

    public String getReason(){
        return reason;
    }
}
