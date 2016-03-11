package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

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
    public Result<User> domainObject() {
        try{
            return Result.success(new User(this.firstName, this.lastName));
        } catch (IllegalArgumentValidationException e){
            return Result.error(e.getMessage());
        }
    }
}
