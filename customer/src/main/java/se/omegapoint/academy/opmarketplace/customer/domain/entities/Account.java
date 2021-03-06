package se.omegapoint.academy.opmarketplace.customer.domain.entities;

import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.*;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

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

    public static AccountCreated createAccount(AccountCreationRequested request, AccountRepository repository){
        notNull(request);
        isTrue(!repository.accountInExistence(request.email()), "Account already exists.");
        return new AccountCreated(request.email(), request.user());
    }

    public static AccountCreditDeposited depositCredits(AccountCreditDepositRequested request, AccountRepository repository){
        notNull(request);
        isTrue(repository.accountInExistence(request.email()), "Account does not exist.");
        return new AccountCreditDeposited(request.email(), request.credits());
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

    public AccountCreditWithdrawn withdrawCredits(AccountCreditWithdrawalRequested request){
        notNull(request);
        isTrue(this.email.equals(request.email()));
        if (request.credits().amount() <= this.vault.amount()) {
            return new AccountCreditWithdrawn(this.email, request.credits());
        } else {
            throw new IllegalArgumentValidationException("Insufficient funds.");
        }
    }

    public AccountCreditWithdrawn charge(ItemOrdered request){
        notNull(request);
        isTrue(this.email.equals(request.order().buyerId()));
        if (request.order().sum().amount() <= this.vault.amount()) {
            return new AccountCreditWithdrawn(this.email, request.order().sum());
        } else {
            throw new IllegalArgumentValidationException("Insufficient funds.");
        }
    }

    public AccountCreditDeposited depositCredits(ItemOrdered request){
        notNull(request);
        isTrue(this.email.equals(request.order().sellerId()));
        return new AccountCreditDeposited(this.email(), request.order().sum());
    }
}
