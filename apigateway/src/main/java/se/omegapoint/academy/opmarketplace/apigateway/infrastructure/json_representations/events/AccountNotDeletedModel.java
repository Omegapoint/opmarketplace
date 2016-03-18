package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.ReasonModel;

public class AccountNotDeletedModel implements JsonModel{
    public static final String TYPE = "AccountNotDeleted";

    private EmailModel email;
    private ReasonModel reason;

    public AccountNotDeletedModel() {}

    public EmailModel getEmail() {
        return email;
    }

    public ReasonModel getReason(){
        return reason;
    }
}
