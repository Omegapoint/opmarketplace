package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChangeRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class AccountUserChangeRequestedModel implements DTO {
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