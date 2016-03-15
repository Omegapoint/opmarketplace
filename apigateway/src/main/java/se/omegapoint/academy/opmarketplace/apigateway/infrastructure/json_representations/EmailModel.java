package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;


import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class EmailModel implements JsonModel {

    private String address;

    public EmailModel(String email){
        this.address = notNull(email);
    }

    public EmailModel(){}

    public String getAddress() {
        return address;
    }

}
