package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

public class AccountUserChanged extends DomainEvent implements AggregateModification {

    public static final String CHANNEL = "Account";

    private AggregateIdentity identity;
    private User user;
    private Timestamp time;

    public AccountUserChanged(Email id, User user) {
        this.user = user;
        this.identity = new AggregateIdentity(id.address(), Account.class.getSimpleName());
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public AccountUserChanged(Email id, User user, Timestamp time) {
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
