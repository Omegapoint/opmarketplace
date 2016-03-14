package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.Result;
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
    public Result<AccountCreationRequested> domainObject() {
        if (!this.email.domainObject().isSuccess())
            return Result.error(email.domainObject().error());
        if (!this.user.domainObject().isSuccess())
            return Result.error(user.domainObject().error());
        try {
            return Result.success(new AccountCreationRequested(email.domainObject().value(), user.domainObject().value(), time));
        } catch (IllegalArgumentValidationException e) {
            return Result.error(e.getMessage());
        }
    }
}
