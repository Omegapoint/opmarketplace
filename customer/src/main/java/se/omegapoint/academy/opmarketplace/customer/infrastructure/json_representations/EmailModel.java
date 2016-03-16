package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class EmailModel implements JsonModel {

    private String address;

    public EmailModel(Email email){
        notNull(email);
        this.address = email.address();
    }

    // TODO: 16/03/16 Maybe remove?
    public EmailModel(String email) {
        notNull(email);
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
