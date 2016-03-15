package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;

public class UserModel implements JsonModel {

    private String firstName;
    private String lastName;

    public UserModel(User user){
        //TODO [dd] add notNull contracts
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

    @Override
    public User domainObject() {
        return new User(this.firstName, this.lastName);
    }
}
