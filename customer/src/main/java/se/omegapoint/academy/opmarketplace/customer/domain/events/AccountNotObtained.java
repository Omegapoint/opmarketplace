package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountNotObtained extends DomainEvent {

    // TODO: 16/03/16 Model as unsafe Email?
    private final String email;
    // TODO: 16/03/16 Create value object Reason?
    private final String reason;

    public AccountNotObtained(String email, String reason) {
        this.email = notNull(email);
        this.reason = notNull(reason);
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
