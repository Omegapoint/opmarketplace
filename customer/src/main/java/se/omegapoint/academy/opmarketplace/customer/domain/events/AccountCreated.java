package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class AccountCreated extends DomainEvent implements AggregateModification {

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
        isTrue(time.before(new Timestamp(System.currentTimeMillis() + 1)));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCreated that = (AccountCreated) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (aggregate != null ? !aggregate.equals(that.aggregate) : that.aggregate != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (aggregate != null ? aggregate.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
