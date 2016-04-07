package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;

import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditWithdrawn implements DomainEvent, PersistableEvent {

    private final Email email;
    private final Credit credit;
    private final Timestamp timestamp;

    public AccountCreditWithdrawn(Email email, Credit credit){
        this(email, credit, new Timestamp(System.currentTimeMillis()));
    }

    public AccountCreditWithdrawn(Email email, Credit credit, Timestamp timestamp){
        isTrue(timestamp.before(new Timestamp(System.currentTimeMillis() + 1)));
        this.email = notNull(email);
        this.credit = notNull(credit);
        this.timestamp = notNull(timestamp);
    }

    public Email email() {
        return email;
    }

    public Credit credit() {
        return credit;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
