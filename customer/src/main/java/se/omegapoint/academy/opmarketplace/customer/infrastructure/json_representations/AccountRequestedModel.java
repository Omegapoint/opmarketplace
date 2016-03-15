package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequestedModel implements JsonModel {

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
}

