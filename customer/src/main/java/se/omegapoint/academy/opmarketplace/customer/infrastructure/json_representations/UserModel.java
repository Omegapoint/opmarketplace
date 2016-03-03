package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.User;

public class UserModel {

    private String firstName;
    private String lastName;

    public UserModel(User user){
        firstName = user.firstName();
        lastName = user.lastName();
    }

    public UserModel(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
