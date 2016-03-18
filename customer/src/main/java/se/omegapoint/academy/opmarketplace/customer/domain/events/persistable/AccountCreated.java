package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;
import java.util.Objects;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class AccountCreated extends DomainEvent implements Persistable {

    private final Email email;
    private final User user;
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
        this.time = time;
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }

    @Override
    public Timestamp timestamp() {
        return time;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCreated that = (AccountCreated) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(user, that.user) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, user, time);
    }
}
