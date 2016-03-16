package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserModel;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreationRequestedModel implements JsonModel {
    public static final String TYPE = "AccountCreationRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountCreationRequestedModel(){
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
