package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;
import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountUserChanged extends DomainEvent {

    private final Email email;
    private final User user;
    private final Timestamp timestamp;

    public AccountUserChanged(Email email, User user) {
        this(email, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountUserChanged(Email email, User user, Timestamp timestamp){
        isTrue(timestamp.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.email = notNull(email);
        this.user = notNull(user);
        this.timestamp = notNull(timestamp);
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountUserChanged that = (AccountUserChanged) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(user, that.user) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, user, timestamp);
    }
}
