package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.AccountDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedDTO implements Event, Serializer {

    public static final String TYPE = "AccountObtained";

    private String requestId;
    private AccountDTO account;

    public AccountObtainedDTO() {}

    public AccountObtainedDTO(AccountObtained accountObtained, String requestId) {
        notNull(accountObtained);
        this.requestId = notNull(requestId);
        this.account = new AccountDTO(accountObtained.account());
    }

    public String getRequestId() {
        return requestId;
    }

    public AccountDTO getAccount() {
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