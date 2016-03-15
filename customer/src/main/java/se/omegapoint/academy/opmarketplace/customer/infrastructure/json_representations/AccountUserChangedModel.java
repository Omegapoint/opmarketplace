package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountUserChangedModel implements JsonModel {
    public static final String TYPE = "AccountUserChanged";

    private AggregateIdentityModel aggregateIdentity;
    private UserModel user;
    private Timestamp time;

    public AccountUserChangedModel(){}

    public AccountUserChangedModel(se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged accountUserChanged) {
        this.aggregateIdentity = new AggregateIdentityModel(new AggregateIdentity(accountUserChanged.aggregateMemberId(), accountUserChanged.aggregateName()));
        this.user = new UserModel(accountUserChanged.user());
        this.time = accountUserChanged.timestamp();
    }

    public AggregateIdentityModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public UserModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountUserChanged domainObject() {
        return new AccountUserChanged(aggregateIdentity.domainObject(), user.domainObject(), time);
    }
}
