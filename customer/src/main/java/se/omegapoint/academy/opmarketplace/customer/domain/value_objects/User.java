package se.omegapoint.academy.opmarketplace.customer.domain.value_objects;


public final class User {
    private final FirstName firstName;
    private final LastName lastName;

    public User(String firstName, String lastName) {
        this.firstName = new FirstName(firstName);
        this.lastName = new LastName(lastName);
    }

    public String firstName(){
        return firstName.name();
    }

    public String lastName(){
        return lastName.name();
    }
}
