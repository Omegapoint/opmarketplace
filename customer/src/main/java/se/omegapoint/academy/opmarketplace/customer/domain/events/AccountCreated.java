package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class AccountCreated extends DomainEvent implements AggregateModification {

    public static final String CHANNEL = "Account";
    public static final String NAME = "AccountCreated";

    private final Email email;
    private final User user;
    private final AggregateIdentity aggregate;
    private final Timestamp time;

    public AccountCreated(Email email, User user){
        this(email, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountCreated(Email email, User user, Timestamp time){
        notNull(email);
        notNull(user);
        notNull(time);
        isTrue(time.before(new Timestamp(System.currentTimeMillis())));
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
