package se.omegapoint.academy.opmarketplace.customer.domain.entities;

import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.*;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

import static se.sawano.java.commons.lang.validate.Validate.*;


public class Account {
    private final Email email;
    private final User user;
    private final Credit vault;

    public Account(Email email, User user, Credit vault) {
        this.email = notNull(email);
        this.user = notNull(user);
        this.vault = notNull(vault);
    }

    public String id(){
        return email.address();
    }

    public Email email(){
        return email;
    }

    public User user(){
        return user;
    }

    public Credit vault(){
        return vault;
    }

    public static AccountCreated createAccount(AccountCreationRequested request){
        notNull(request);
        return new AccountCreated(request.email(), request.user());
    }

    public AccountUserChanged changeUser(AccountUserChangeRequested request){
        notNull(request);
        isTrue(this.email.equals(request.email()));
        return new AccountUserChanged(this.email(), request.user());
    }

    public AccountDeleted deleteAccount(AccountDeletionRequested request) {
        notNull(request);
        isTrue(this.email.equals(request.email()));
        return new AccountDeleted(this.email());
    }

    public AccountCreditDeposited depositCredits(AccountCreditDepositRequested request){
        notNull(request);
        isTrue(this.email.equals(request.email()));
        return new AccountCreditDeposited(this.email(), request.credits());
    }

    public AccountCreditWithdrawn charge(ItemOrdered request){
        notNull(request);
        isTrue(this.email.equals(request.order().buyerId()));
        return new AccountCreditWithdrawn(this.email, request.order().sum());
    }

    public AccountCreditDeposited depositCredits(ItemOrdered request){
        notNull(request);
        isTrue(this.email.equals(request.order().sellerId()));
        return new AccountCreditDeposited(this.email(), request.order().sum());
    }
}
