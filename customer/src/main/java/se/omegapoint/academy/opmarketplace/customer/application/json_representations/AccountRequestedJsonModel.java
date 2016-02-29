package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;

import java.sql.Timestamp;

public class AccountRequestedJsonModel {
    private EmailJsonModel email;
    private UserJsonModel user;
    private Timestamp time;


    public AccountRequestedJsonModel(){}

    public AccountRequestedJsonModel(AccountRequested accountRequested) {
        this.email = new EmailJsonModel(accountRequested.email());
        this.user = new UserJsonModel(accountRequested.user());
        this.time = accountRequested.time();
    }

    public EmailJsonModel getEmail() {
        return email;
    }

    public UserJsonModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountRequested domainEvent(){
        return new AccountRequested(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), time);
    }
}
