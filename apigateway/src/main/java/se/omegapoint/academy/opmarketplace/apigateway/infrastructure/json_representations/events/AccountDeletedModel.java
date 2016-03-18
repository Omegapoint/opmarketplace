package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;

public class AccountDeletedModel implements JsonModel {
    public static final String TYPE = "AccountDeleted";

    private EmailModel email;

    public AccountDeletedModel() {}

    public EmailModel getEmail() {
        return email;
    }
}
