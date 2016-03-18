package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

import java.sql.Timestamp;

public class AccountCreationRequestedModel implements DTO {
    public static final String TYPE = "AccountCreationRequested";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountCreationRequestedModel(){}

    public AccountCreationRequestedModel(AccountCreationRequested accountCreationRequested) {
        this.email = new EmailModel(accountCreationRequested.email());
        this.user = new UserModel(accountCreationRequested.user());
        this.timestamp = accountCreationRequested.timestamp();
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
    public AccountCreationRequested domainObject() {
        return new AccountCreationRequested(email.domainObject(), user.domainObject(), timestamp);
    }
}
