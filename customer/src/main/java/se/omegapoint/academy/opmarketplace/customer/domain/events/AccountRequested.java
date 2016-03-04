package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequested extends DomainEvent{

    public static final String NAME = "AccountRequested";

    private Email email;
    private User user;
    private Timestamp time;

    public AccountRequested(Email email, User user){
        this(email, user, new Timestamp(System.currentTimeMillis()));
    }

    public AccountRequested(Email email, User user, Timestamp time){
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
}
