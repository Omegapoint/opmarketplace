package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChanged extends DomainEvent implements AggregateModification {

    public static final String NAME = "AccountUserChanged";

    private AggregateIdentity identity;
    private User user;
    private Timestamp time;

    public AccountUserChanged(Email id, User user) {
        this(id, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountUserChanged(Email id, User user, Timestamp time) {
        notNull(id);
        notNull(user);
        notNull(time);
        isTrue(time.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.user = user;
        this.identity = new AggregateIdentity(id.address(), Account.class.getSimpleName());
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
}
