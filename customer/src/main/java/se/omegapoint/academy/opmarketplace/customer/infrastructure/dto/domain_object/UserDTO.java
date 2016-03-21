package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserDTO implements DTO, Serializer, Deserializer<User> {

    public final String firstName;
    public final String lastName;

    public UserDTO(User user){
        notNull(user);
        firstName = user.firstName();
        lastName = user.lastName();
    }

    @JsonCreator
    public UserDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName){
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }

    @Override
    public User domainObject() {
        return new User(this.firstName, this.lastName);
    }
}
