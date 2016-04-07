package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditWithdrawn;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@Entity
public class AccountCreditWithdrawnModel {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private Integer credit;
    private Timestamp time;

    public AccountCreditWithdrawnModel(){}

    public AccountCreditWithdrawnModel(AccountCreditWithdrawn accountCreditWithdrawn) {
        notNull(accountCreditWithdrawn);
        this.email = accountCreditWithdrawn.email().address();
        this.credit = accountCreditWithdrawn.credit().amount();
        this.time = accountCreditWithdrawn.timestamp();
    }

    public AccountCreditWithdrawn domainEvent(){
        return new AccountCreditWithdrawn(new Email(email), new Credit(credit), time);
    }
}
