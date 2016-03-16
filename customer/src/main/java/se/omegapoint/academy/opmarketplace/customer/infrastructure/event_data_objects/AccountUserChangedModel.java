package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

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
        //TODO [dd] add notNull contracts
        this.email = accountUserChanged.email().address();
        this.userFirstName = accountUserChanged.user().firstName();
        this.userLastName = accountUserChanged.user().lastName();
        this.time = accountUserChanged.timestamp();
    }

    public AccountUserChanged domainEvent(){
        return new AccountUserChanged(new Email(email), new User(userFirstName, userLastName), time);
    }
}
