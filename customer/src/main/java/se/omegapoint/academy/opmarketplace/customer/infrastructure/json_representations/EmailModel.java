package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

public class EmailModel implements JsonModel {

    private String address;

    public EmailModel(Email email){
        //TODO [dd] add notNull contracts
        this.address = email.address();
    }

    private EmailModel(String email){
        this.address = email;
    }

    public EmailModel(){}

    public String getAddress() {
        return address;
    }

    @Override
    public Email domainObject() {
        return new Email(this.address);
    }
}
