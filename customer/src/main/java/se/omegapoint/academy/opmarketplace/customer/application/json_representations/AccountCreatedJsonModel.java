package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountCreatedJsonModel implements JsonModel{

    private AggregateIdentityJsonModel aggregateIdentity;
    private EmailJsonModel email;
    private UserJsonModel user;
    private Timestamp time;


    public AccountCreatedJsonModel(){}

    public AccountCreatedJsonModel(AccountCreated accountCreated) {
        this.aggregateIdentity = new AggregateIdentityJsonModel(new AggregateIdentity(accountCreated.aggregateMemberId(), accountCreated.aggregateName()));
        this.email = new EmailJsonModel(accountCreated.email());
        this.user = new UserJsonModel(accountCreated.user());
        this.time = accountCreated.time();
    }

    public AggregateIdentityJsonModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public EmailJsonModel getEmail() {
        return email;
    }

    public UserJsonModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountCreated domainEvent(){
        return new AccountCreated(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), time);
    }
}
