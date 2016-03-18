package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

import java.sql.Timestamp;

public class AccountUserChangedModel implements DTO {
    public static final String TYPE = "AccountUserChanged";

    private EmailModel email;
    private UserModel user;
    private Timestamp timestamp;

    public AccountUserChangedModel(){}

    public AccountUserChangedModel(AccountUserChanged accountUserChanged) {
        this.email = new EmailModel(accountUserChanged.email());
        this.user = new UserModel(accountUserChanged.user());
        this.timestamp = accountUserChanged.timestamp();
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

    // TODO: 16/03/16 No usage for this
    public AccountUserChanged domainObject() {
        return new AccountUserChanged(email.domainObject(), user.domainObject(), timestamp);
    }
}