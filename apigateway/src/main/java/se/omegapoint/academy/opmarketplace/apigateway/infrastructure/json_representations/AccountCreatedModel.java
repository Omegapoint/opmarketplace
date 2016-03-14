package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import java.sql.Timestamp;

public class AccountCreatedModel implements JsonModel {
    public static final String TYPE = "AccountCreated";

    private AggregateIdentityModel aggregateIdentity;
    private EmailModel email;
    private UserModel user;
    private Timestamp time;


    public AccountCreatedModel(){}

    public AggregateIdentityModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

}
