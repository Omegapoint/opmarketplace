package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.Result;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

public class EmailModel implements JsonModel {

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

    @Override
    public Result<Email> domainObject() {
        try{
            return Result.success(new Email(this.address));
        } catch (IllegalArgumentValidationException e){
            return Result.error(e.getMessage());
        }
    }
}
