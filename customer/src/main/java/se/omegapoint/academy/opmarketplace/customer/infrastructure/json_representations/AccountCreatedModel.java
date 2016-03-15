package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import java.sql.Timestamp;

public class AccountCreatedModel implements JsonModel {
    public static final String TYPE = "AccountCreated";

    private AggregateIdentityModel aggregateIdentity;
    private EmailModel email;
    private UserModel user;
    private Timestamp time;


    public AccountCreatedModel(){}

    public AccountCreatedModel(se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated accountCreated) {
        //TODO [dd] add notNull contracts
        this.aggregateIdentity = new AggregateIdentityModel(new AggregateIdentity(accountCreated.aggregateMemberId(), accountCreated.aggregateName()));
        this.email = new EmailModel(accountCreated.email());
        this.user = new UserModel(accountCreated.user());
        this.time = accountCreated.timestamp();
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

    public AccountCreated domainObject() {
        return new AccountCreated(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), time);
    }
}
