package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotCreated extends DomainEvent {

    // TODO: 16/03/16 Model as unsafe Email?
    private final String email;
    // TODO: 16/03/16 Create value object Reason?
    private final String reason;
    private final Timestamp timestamp;

    public AccountNotCreated(String email, String reason) {
        this.email = notNull(email);
        this.reason = notNull(reason);
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String email() {
        return email;
    }

    public String reason() {
        return reason;
    }

    @Override
    public Timestamp timestamp() {
        return null;
    }
}
