package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountUserChangedJsonModel implements JsonModel{
    private AggregateIdentityJsonModel aggregateIdentity;
    private UserJsonModel user;
    private Timestamp time;

    public AccountUserChangedJsonModel(){}

    public AccountUserChangedJsonModel(AccountUserChanged accountUserChanged) {
        this.aggregateIdentity = new AggregateIdentityJsonModel(new AggregateIdentity(accountUserChanged.aggregateMemberId(), accountUserChanged.aggregateName()));
        this.user = new UserJsonModel(accountUserChanged.user());
        this.time = accountUserChanged.time();
    }

    public AggregateIdentityJsonModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public UserJsonModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountUserChanged domainEvent(){
        return new AccountUserChanged(new Email(aggregateIdentity.getId()), new User(user.getFirstName(), user.getLastName()));
    }
}
