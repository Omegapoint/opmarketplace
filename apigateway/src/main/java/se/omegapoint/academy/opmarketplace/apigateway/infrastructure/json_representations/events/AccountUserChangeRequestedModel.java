package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangeRequestedModel implements JsonModel {

    public static final String TYPE = "AccountUserChangeRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountUserChangeRequestedModel() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
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
