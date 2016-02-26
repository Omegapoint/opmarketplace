package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

public final class AccountCreated extends DomainEvent implements AggregateModification {

    public static final String CHANNEL = "Account";

    private Email email;
    private User user;
    private AggregateIdentity aggregate;
    private Timestamp time;

    public AccountCreated(Email email, User user){
        this.email = email;
        this.user = user;
        this.aggregate = new AggregateIdentity(email.address(), Account.class.getSimpleName());
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public AccountCreated(Email email, User user, Timestamp time){
        this.email = email;
        this.user = user;
        this.aggregate = new AggregateIdentity(email.address(), Account.class.getSimpleName());
        this.time = time;
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }

    @Override
    public Timestamp time() {
        return time;
    }

    @Override
    public String aggregateMemberId() {
        return aggregate.id();
    }

    @Override
    public String aggregateName() {
        return aggregate.aggregateName();
    }
}
