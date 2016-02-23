package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;

public class EmailModel {

    private String address;

    public EmailModel(Email email){
        this.address = email.address();
    }

    public EmailModel(String address) {
        this.address = address;
    }

    public EmailModel(){}

    public String getAddress() {
        return address;
    }
}
