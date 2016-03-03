package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountCreatedModel implements JsonModel{

    private AggregateIdentityModel aggregateIdentity;
    private EmailModel email;
    private UserModel user;
    private Timestamp time;


    public AccountCreatedModel(){}

    public AccountCreatedModel(AccountCreated accountCreated) {
        this.aggregateIdentity = new AggregateIdentityModel(new AggregateIdentity(accountCreated.aggregateMemberId(), accountCreated.aggregateName()));
        this.email = new EmailModel(accountCreated.email());
        this.user = new UserModel(accountCreated.user());
        this.time = accountCreated.time();
    }

    public AggregateIdentityModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountCreated domainEvent(){
        return new AccountCreated(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), time);
    }
}
