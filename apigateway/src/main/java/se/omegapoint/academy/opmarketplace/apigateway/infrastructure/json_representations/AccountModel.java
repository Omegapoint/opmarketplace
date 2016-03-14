package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

public class AccountModel {
    private EmailModel email;
    private UserModel user;

    public AccountModel(){}

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }
}
