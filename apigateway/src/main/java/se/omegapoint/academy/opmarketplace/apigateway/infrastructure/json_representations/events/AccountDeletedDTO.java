package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;

public class AccountDeletedDTO implements JsonModel {
    public static final String TYPE = "AccountDeleted";

    private EmailDTO email;

    public AccountDeletedDTO() {}

    public EmailDTO getEmail() {
        return email;
    }
}
