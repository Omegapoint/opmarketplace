package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.AccountModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountObtained";

    private String requestId;
    private AccountModel account;

    public AccountObtainedModel() {}

    public AccountObtainedModel(AccountObtained accountObtained, String requestId) {
        notNull(accountObtained);
        this.requestId = notNull(requestId);
        this.account = new AccountModel(accountObtained.account());
    }

    public String getRequestId() {
        return requestId;
    }

    public AccountModel getAccount() {
        return account;
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