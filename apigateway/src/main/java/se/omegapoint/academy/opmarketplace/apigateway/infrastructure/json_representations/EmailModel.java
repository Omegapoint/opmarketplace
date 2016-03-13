package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;


public class EmailModel implements JsonModel {

    private String address;

    private EmailModel(String email){
        this.address = email;
    }

    public EmailModel(){}

    public String getAddress() {
        return address;
    }

}
