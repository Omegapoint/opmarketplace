package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountUserChangedModel implements JsonModel{
    private AggregateIdentityModel aggregateIdentity;
    private UserModel user;
    private Timestamp time;

    public AccountUserChangedModel(){}

    public AccountUserChangedModel(AccountUserChanged accountUserChanged) {
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

    public AccountUserChanged domainEvent(){
        return new AccountUserChanged(new Email(aggregateIdentity.getId()), new User(user.getFirstName(), user.getLastName()));
    }
}
