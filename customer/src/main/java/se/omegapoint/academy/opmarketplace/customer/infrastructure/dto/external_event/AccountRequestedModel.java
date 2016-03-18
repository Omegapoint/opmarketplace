package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequestedModel implements DTO, Event, Deserializer<AccountRequested> {

    public static final String TYPE = "AccountRequested";

    private EmailModel email;
    private Timestamp timestamp;

    public AccountRequestedModel() {}

    public AccountRequestedModel(EmailModel email, Timestamp timestamp) {
        this.email = notNull(email);
        this.timestamp = notNull(timestamp);
    }

    public EmailModel getEmail() {
        return email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public AccountRequested domainObject() {
        return new AccountRequested(new Email(email.getAddress()), timestamp);
    }

    @Override
    public String type() {
        return TYPE;
    }
}

