package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedModel implements JsonModel {
    public static final String TYPE = "AccountCreated";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountCreatedModel(){}

    public AccountCreatedModel(AccountCreated accountCreated) {
        notNull(accountCreated);
        this.email = new EmailModel(accountCreated.email());
        this.user = new UserModel(accountCreated.user());
        this.timestamp = accountCreated.timestamp();
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

    // TODO: 16/03/16 No usage for this method.
    public AccountCreated domainObject() {
        return new AccountCreated(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), timestamp);
    }
}
