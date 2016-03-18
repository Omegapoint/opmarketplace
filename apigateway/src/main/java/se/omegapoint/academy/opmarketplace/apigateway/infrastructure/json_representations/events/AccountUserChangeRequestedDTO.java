package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserDTO;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangeRequestedDTO implements JsonModel {

    public static final String TYPE = "AccountUserChangeRequested";

    private EmailDTO email;
    private UserDTO user;
    private Timestamp timestamp;

    public AccountUserChangeRequestedDTO() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
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
