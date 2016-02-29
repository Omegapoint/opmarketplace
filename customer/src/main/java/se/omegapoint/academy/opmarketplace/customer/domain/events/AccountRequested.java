package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;

import java.sql.Timestamp;

public class AccountRequested extends DomainEvent{

    public static final String CHANNEL = "Account";
    public static final String NAME = "AccountRequested";

    private Email email;
    private User user;
    private Timestamp time;

    public AccountRequested(Email email, User user){
        this.email = email;
        this.user = user;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public AccountRequested(Email email, User user, Timestamp time){
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
