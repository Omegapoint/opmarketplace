package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountUserChangeRequestedModel implements JsonModel {

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountUserChangeRequestedModel() {}

    public AccountUserChangeRequestedModel(AccountUserChangeRequested request) {
        notNull(request);
        this.email = new EmailModel(notNull(request.email()));
        this.user = new UserModel(notNull(request.user()));
        this.timestamp = notNull(request.timestamp());
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
