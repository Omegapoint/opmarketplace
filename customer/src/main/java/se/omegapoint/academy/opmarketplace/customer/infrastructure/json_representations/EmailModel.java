package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;

public class EmailModel {

    private String address;

    public EmailModel(Email email){
        this.address = email.address();
    }

    private EmailModel(String email){
        this.address = email;
    }

    public EmailModel(){}

    public String getAddress() {
        return address;
    }
}
