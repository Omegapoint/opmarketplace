package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreationRequested extends DomainEvent{

    public static final String NAME = "AccountCreationRequested";

    private final Email email;
    private final User user;
    private final Timestamp time;

    public AccountCreationRequested(Email email, User user){
        this(email, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountCreationRequested(Email email, User user, Timestamp time){
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
    public Timestamp time() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCreationRequested that = (AccountCreationRequested) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
