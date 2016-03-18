package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class AccountCreated implements DomainEvent, PersistableEvent {

    private final Email email;
    private final User user;
    private final Timestamp time;

    public AccountCreated(Email email, User user){
        this(email, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountCreated(Email email, User user, Timestamp time){
        isTrue(time.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.email = notNull(email);
        this.user = notNull(user);
        this.time = notNull(time);
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
}
