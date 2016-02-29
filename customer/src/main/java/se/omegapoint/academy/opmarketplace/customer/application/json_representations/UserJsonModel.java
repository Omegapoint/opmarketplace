package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.User;

public class UserJsonModel {

    private String firstName;
    private String lastName;

    public UserJsonModel(User user){
        firstName = user.firstName();
        lastName = user.lastName();
    }

    public UserJsonModel(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
