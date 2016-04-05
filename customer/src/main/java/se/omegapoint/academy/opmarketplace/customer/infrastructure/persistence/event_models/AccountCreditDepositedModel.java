package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditDeposited;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@Entity
public class AccountCreditDepositedModel {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private Integer credit;
    private Timestamp time;

    public AccountCreditDepositedModel(){}

    public AccountCreditDepositedModel(AccountCreditDeposited accountCreditDeposited) {
        notNull(accountCreditDeposited);
        this.email = accountCreditDeposited.email().address();
        this.credit = accountCreditDeposited.credit().amount();
        this.time = accountCreditDeposited.timestamp();
    }

    public AccountCreditDeposited domainEvent(){
        return new AccountCreditDeposited(new Email(email), new Credit(credit), time);
    }
}
