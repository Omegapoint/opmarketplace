package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeleted implements DomainEvent, PersistableEvent {

    private final Email email;
    private final Timestamp timestamp;

    public AccountDeleted(Email email) {
        this(email, new Timestamp(System.currentTimeMillis()));
    }

    public AccountDeleted(Email email, Timestamp timestamp) {
        isTrue(timestamp.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.email = notNull(email);
        this.timestamp = notNull(timestamp);
    }

    public Email email() {
        return email;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
