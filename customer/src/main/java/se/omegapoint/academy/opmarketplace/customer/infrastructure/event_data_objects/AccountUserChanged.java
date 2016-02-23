package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountUserChanged implements DataObject {

    private String firstName;
    private String lastName;

    public AccountUserChanged(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AccountUserChanged(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    @Override
    public String json() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
