package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountModel {
    private EmailModel email;
    private UserModel user;

    public AccountModel(){}

    public AccountModel(Account account) {
        notNull(account);
        this.email = new EmailModel(account.email());
        this.user = new UserModel(account.user());
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }
}
