package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class EmailDTO implements DTO, Serializer, Deserializer<Email> {

    private String address;

    public EmailDTO(Email email){
        notNull(email);
        this.address = email.address();
    }

    // TODO: 16/03/16 Used for unsafe events, maybe remove?
    public EmailDTO(String email) {
        notNull(email);
        this.address = email;
    }

    public EmailDTO(){}

    public String getAddress() {
        return address;
    }

    @Override
    public Email domainObject() {
        return new Email(this.address);
    }
}
