package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserDTO;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountCreationRequestedDTO implements JsonModel {
    public static final String TYPE = "AccountCreationRequested";

    private EmailDTO email;
    private UserDTO user;
    private Timestamp timestamp;

    public AccountCreationRequestedDTO(){
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public EmailDTO getEmail() {
        return email;
    }

    public UserDTO getUser() {
        return user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
