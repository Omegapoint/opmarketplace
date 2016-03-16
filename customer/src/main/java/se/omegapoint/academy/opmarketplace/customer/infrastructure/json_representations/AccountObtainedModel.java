package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountObtained;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountObtainedModel implements JsonModel{

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

    // TODO: 16/03/16 No usage for this method
    @Override
    public AccountObtained domainObject() {
        return new AccountObtained(new Account(
                new Email(account.getEmail().getAddress()),
                new User(account.getUser().getFirstName(),
                        account.getUser().getLastName()
                )
        ), timestamp);
    }
}