package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreationRequested extends DomainEvent{

    private final Email email;
    private final User user;
    private final Timestamp timestamp;

    public AccountCreationRequested(Email email, User user, Timestamp timestamp){
        notNull(email);
        notNull(user);
        notNull(timestamp);
        isTrue(timestamp.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.email = email;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Email email() {
        return email;
    }

    public User user() {
        return user;
    }

    @Override
    public Timestamp time() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCreationRequested that = (AccountCreationRequested) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
