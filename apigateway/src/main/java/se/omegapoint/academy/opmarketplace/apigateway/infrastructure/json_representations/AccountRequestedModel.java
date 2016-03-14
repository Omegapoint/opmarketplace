package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import java.sql.Timestamp;

public class AccountRequestedModel implements JsonModel {
    private EmailModel email;
    private UserModel user;
    private Timestamp time;

    public AccountRequestedModel(){
        time = new Timestamp(System.currentTimeMillis());
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
