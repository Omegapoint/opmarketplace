package se.omegapoint.academy.opmarketplace.customer.domain;

public class Account {
    private Email email;

    public Account(Email email) {
        this.email = email;
    }

    public Account(String email) {
        this.email = new Email(email);
    }
}
