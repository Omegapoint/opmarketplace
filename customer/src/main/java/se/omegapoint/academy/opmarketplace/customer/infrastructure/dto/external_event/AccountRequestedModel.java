package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequestedModel implements DTO, Event, Deserializer<AccountRequested> {

    public static final String TYPE = "AccountRequested";

    private String requestId;
    private EmailModel email;

    public AccountRequestedModel() {}

    public String getRequestId() {
        return requestId;
    }

    public EmailModel getEmail() {
        return email;
    }

    @Override
    public AccountRequested domainObject() {
        return new AccountRequested(new Email(email.getAddress()));
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}