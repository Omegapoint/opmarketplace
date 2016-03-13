package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

public class UserModel implements JsonModel {

    private String firstName;
    private String lastName;

    public UserModel(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
