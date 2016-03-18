package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountObtained;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.AccountModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedModel implements DTO, Event, Serializer {

    public static final String TYPE = "AccountObtained";

    private AccountModel account;
    private Timestamp timestamp;

    public AccountObtainedModel() {}

    public AccountObtainedModel(AccountObtained accountObtained) {
        notNull(accountObtained);
        this.account = new AccountModel(accountObtained.account());
        this.timestamp = accountObtained.timestamp();
    }

    public AccountModel getAccount() {
        return account;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String type() {
        return TYPE;
    }
}