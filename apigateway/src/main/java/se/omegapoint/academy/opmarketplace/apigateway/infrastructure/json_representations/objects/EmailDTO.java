package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects;


import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class EmailDTO implements JsonModel {

    private String address;

    public EmailDTO(String email){
        this.address = notNull(email);
    }

    public EmailDTO(){}

    public String getAddress() {
        return address;
    }

}
