package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserDTO implements JsonModel {

    private String firstName;
    private String lastName;

    public UserDTO(){}

    public UserDTO(String firstName, String lastName) {
        this.firstName = notNull(firstName);
        this.lastName = notNull(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
