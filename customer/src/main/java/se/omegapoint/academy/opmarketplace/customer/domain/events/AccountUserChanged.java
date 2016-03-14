package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChanged extends DomainEvent implements AggregateModification {

    private final AggregateIdentity identity;
    private final User user;
    private final Timestamp time;

    public AccountUserChanged(Email id, User user) {
        this(id, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountUserChanged(Email id, User user, Timestamp time) {
        this(new AggregateIdentity(id.address(), Account.class.getSimpleName()), user, time);
    }

    public AccountUserChanged(AggregateIdentity identity, User user, Timestamp time){
        notNull(identity);
        notNull(user);
        notNull(time);
        isTrue(time.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.user = user;
        this.identity = identity;
        this.time = time;
    }

    public User user() {
        return user;
    }

    @Override
    public String aggregateMemberId() {
        return identity.id();
    }

    @Override
    public String aggregateName() {
        return identity.aggregateName();
    }

    @Override
    public Timestamp time() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountUserChanged that = (AccountUserChanged) o;

        if (identity != null ? !identity.equals(that.identity) : that.identity != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;

    }

    @Override
    public int hashCode() {
        int result = identity != null ? identity.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
