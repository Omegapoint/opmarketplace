package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AccountCreatedModel {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String userFirstName;
    private String userLastName;
    private Timestamp time;

    public AccountCreatedModel(){}

    public AccountCreatedModel(AccountCreated accountCreated) {
        //TODO [dd] add notNull contracts
        this.email = accountCreated.email().address();
        this.userFirstName = accountCreated.user().firstName();
        this.userLastName = accountCreated.user().lastName();
        this.time = accountCreated.timestamp();
    }

    public AccountCreated domainEvent(){
        return new AccountCreated(new Email(email), new User(userFirstName, userLastName), time);
    }
}
