package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.ReasonDTO;

public class AccountNotDeletedDTO implements JsonModel{
    public static final String TYPE = "AccountNotDeleted";

    private EmailDTO email;
    private ReasonDTO reason;

    public AccountNotDeletedDTO() {}

    public EmailDTO getEmail() {
        return email;
    }

    public ReasonDTO getReason(){
        return reason;
    }
}
