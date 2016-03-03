package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;

import java.sql.Timestamp;

public class AccountRequestedModel {
    private EmailModel email;
    private UserModel user;
    private Timestamp time;


    public AccountRequestedModel(){}

    public AccountRequestedModel(AccountRequested accountRequested) {
        this.email = new EmailModel(accountRequested.email());
        this.user = new UserModel(accountRequested.user());
        this.time = accountRequested.time();
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

    public Timestamp getTime() {
        return time;
    }

    public AccountRequested domainEvent(){
        return new AccountRequested(new Email(email.getAddress()), new User(user.getFirstName(), user.getLastName()), time);
    }
}
