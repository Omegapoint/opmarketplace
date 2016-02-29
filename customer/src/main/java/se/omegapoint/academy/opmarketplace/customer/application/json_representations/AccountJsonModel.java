package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

public class AccountJsonModel {
    private EmailJsonModel email;
    private UserJsonModel user;

    public AccountJsonModel(){}

    public AccountJsonModel(Email email, User user) {
        this.email = new EmailJsonModel(email);
        this.user = new UserJsonModel(user);
    }

    public EmailJsonModel getEmail() {
        return email;
    }

    public UserJsonModel getUser() {
        return user;
    }
}
