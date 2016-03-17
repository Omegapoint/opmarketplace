package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.ReasonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserNotChangedModel implements JsonModel {

    public static final String TYPE = "AccountUserNotChanged";

    private EmailModel email;
    private ReasonModel reason;

    public AccountUserNotChangedModel(){}

    public EmailModel getEmail() {
        return email;
    }

    public ReasonModel getReason() {
        return reason;
    }
}
