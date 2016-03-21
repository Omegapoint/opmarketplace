package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserDTO implements DTO, Serializer, Deserializer<User> {

    private String firstName;
    private String lastName;

    public UserDTO(User user){
        notNull(user);
        firstName = user.firstName();
        lastName = user.lastName();
    }

    public UserDTO(){}

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
