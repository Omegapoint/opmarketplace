package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreationRequestedModel implements JsonModel {
    public static final String TYPE = "AccountCreationRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountCreationRequestedModel(){
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public AccountCreationRequestedModel(EmailModel email, UserModel user){
        this.email = notNull(email);
        this.user = notNull(user);
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
