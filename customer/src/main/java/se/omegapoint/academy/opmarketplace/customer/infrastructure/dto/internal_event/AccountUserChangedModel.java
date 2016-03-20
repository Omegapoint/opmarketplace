package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChangedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountUserChanged";

    private String requestId;
    private EmailModel email;
    private UserModel user;

    public AccountUserChangedModel(){}

    public AccountUserChangedModel(AccountUserChanged accountUserChanged, String requestId) {
        notNull(accountUserChanged);
        this.requestId = notNull(requestId);
        this.email = new EmailModel(accountUserChanged.email());
        this.user = new UserModel(accountUserChanged.user());
    }

    public String getRequestId() {
        return requestId;
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
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
