package se.omegapoint.academy.opmarketplace.customer.domain;

public class Account {
    private final Email email;
    private User user;

    public Account(Email email, User user) {
        this.email = email;
        this.user = user;
    }


    public Account(String email, String firstName, String lastName) {
        this.user = new User(firstName, lastName);
        this.email = new Email(email);
    }

    public String id(){
        return email.address();
    }

    public User user(){
        return user;
    }
}
