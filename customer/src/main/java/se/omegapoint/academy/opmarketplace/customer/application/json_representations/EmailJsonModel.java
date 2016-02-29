package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;

public class EmailJsonModel {

    private String address;

    public EmailJsonModel(Email email){
        this.address = email.address();
    }

    public EmailJsonModel(){}

    public String getAddress() {
        return address;
    }
}
