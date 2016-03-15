package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

public class AccountModel {
    private EmailModel email;
    private UserModel user;

    public AccountModel(){}

    public AccountModel(EmailModel email, UserModel user) {
        this.email = email;
        this.user = user;
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }
}
