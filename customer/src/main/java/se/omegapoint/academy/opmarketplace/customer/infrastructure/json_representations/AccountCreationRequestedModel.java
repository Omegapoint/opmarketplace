package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.sql.Timestamp;

public class AccountCreationRequestedModel implements JsonModel {
    public static final String TYPE = "AccountCreationRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp time;

    public AccountCreationRequestedModel(){}

    public AccountCreationRequestedModel(AccountCreationRequested accountCreationRequested) {
        this.email = new EmailModel(accountCreationRequested.email());
        this.user = new UserModel(accountCreationRequested.user());
        this.time = accountCreationRequested.time();
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

    @Override
    public AccountCreationRequested domainObject() {
        return new AccountCreationRequested(email.domainObject(), user.domainObject(), time);
    }
}
