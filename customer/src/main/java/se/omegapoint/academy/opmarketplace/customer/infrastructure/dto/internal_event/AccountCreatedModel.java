package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreatedModel implements DTO, Event, Serializer {

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

    @Override
    public String type() {
        return TYPE;
    }
}
