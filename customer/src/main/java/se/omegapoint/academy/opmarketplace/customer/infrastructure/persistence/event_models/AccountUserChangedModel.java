package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@Entity
public class AccountUserChangedModel {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String userFirstName;
    private String userLastName;
    private Timestamp time;

    public AccountUserChangedModel(){}

    public AccountUserChangedModel(AccountUserChanged accountUserChanged){
        notNull(accountUserChanged);
        this.email = accountUserChanged.email().address();
        this.userFirstName = accountUserChanged.user().firstName();
        this.userLastName = accountUserChanged.user().lastName();
        this.time = accountUserChanged.timestamp();
    }

    public AccountUserChanged domainEvent(){
        return new AccountUserChanged(new Email(email), new User(userFirstName, userLastName), time);
    }
}
