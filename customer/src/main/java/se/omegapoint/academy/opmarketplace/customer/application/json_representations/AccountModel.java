package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

public class AccountModel {
    private EmailModel email;
    private UserModel user;

    public AccountModel(Email email, User user) {
        this.email = new EmailModel(email);
        this.user = new UserModel(user);
    }

    public AccountModel(EmailModel email, UserModel user) {
        this.email = email;
        this.user = user;
    }

    public AccountModel(){}

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

}
