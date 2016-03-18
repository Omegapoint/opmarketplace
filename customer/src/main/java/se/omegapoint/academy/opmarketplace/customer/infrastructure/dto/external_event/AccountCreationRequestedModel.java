package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

public class AccountCreationRequestedModel implements DTO, Event, Deserializer<AccountCreationRequested> {

    public static final String TYPE = "AccountCreationRequested";

    private EmailModel email;
    private UserModel user;

    public AccountCreationRequestedModel(){}

    public AccountCreationRequestedModel(AccountCreationRequested accountCreationRequested) {
        this.email = new EmailModel(accountCreationRequested.email());
        this.user = new UserModel(accountCreationRequested.user());
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }


    @Override
    public AccountCreationRequested domainObject() {
        return new AccountCreationRequested(email.domainObject(), user.domainObject());
    }

    @Override
    public String type() {
        return TYPE;
    }
}
