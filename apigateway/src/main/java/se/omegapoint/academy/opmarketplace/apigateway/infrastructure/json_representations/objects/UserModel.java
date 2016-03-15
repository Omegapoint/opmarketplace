package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserModel implements JsonModel {

    private String firstName;
    private String lastName;

    public UserModel(){}

    public UserModel(String firstName, String lastName) {
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
