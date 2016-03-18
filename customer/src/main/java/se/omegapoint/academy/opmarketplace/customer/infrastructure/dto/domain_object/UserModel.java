package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserModel implements DTO {

    private String firstName;
    private String lastName;

    public UserModel(User user){
        notNull(user);
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
