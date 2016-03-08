package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

public class AccountModel {
    private EmailModel email;
    private UserModel user;

    public AccountModel(){}

    public AccountModel(Email email, User user) {
        this.email = new EmailModel(email);
        this.user = new UserModel(user);
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }
}
