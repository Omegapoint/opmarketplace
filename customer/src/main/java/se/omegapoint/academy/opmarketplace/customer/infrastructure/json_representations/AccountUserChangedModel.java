package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

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
        this.time = accountUserChanged.time();
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

    public Result<AccountUserChanged> domainObject() {
        if (!this.aggregateIdentity.domainObject().isSuccess())
            return Result.error(aggregateIdentity.domainObject().error());
        if (!this.user.domainObject().isSuccess())
            return Result.error(user.domainObject().error());
        try {
            return Result.success(new se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged(aggregateIdentity.domainObject().value(), user.domainObject().value(), time));
        } catch (IllegalArgumentValidationException e) {
            return Result.error(e.getMessage());
        }
    }
}
