package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@Entity
public class AccountDeletedModel {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private Timestamp time;

    public AccountDeletedModel() {}

    public AccountDeletedModel(AccountDeleted event) {
        notNull(event);
        this.email = event.email().address();
        this.time = event.timestamp();
    }

    public AccountDeleted domainEvent() {
        return new AccountDeleted(new Email(email), time);
    }
}
