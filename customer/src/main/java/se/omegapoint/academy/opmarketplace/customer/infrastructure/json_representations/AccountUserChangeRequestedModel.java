package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChangeRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountUserChangeRequestedModel implements JsonModel {
    public final static String TYPE = "AccountUserChangeRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountUserChangeRequestedModel() {}

    public AccountUserChangeRequestedModel(AccountUserChangeRequested request) {
        notNull(request);
        this.email = new EmailModel(request.email());
        this.user = new UserModel(request.user());
        this.timestamp = request.timestamp();
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

    @Override
    public AccountUserChangeRequested domainObject() {
        return new AccountUserChangeRequested(
                new Email(email.getAddress()),
                new User(user.getFirstName(), user.getLastName()),
                timestamp);
    }
}
