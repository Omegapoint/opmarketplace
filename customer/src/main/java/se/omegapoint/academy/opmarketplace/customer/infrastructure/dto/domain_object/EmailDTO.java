package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class EmailDTO implements DTO, Serializer, Deserializer<Email> {

    public final String address;

    public EmailDTO(Email email){
        notNull(email);
        this.address = email.address();
    }

    @JsonCreator
    public EmailDTO(@JsonProperty("address") String address) {
        this.address = notNull(address);
    }

    @Override
    public Email domainObject() {
        return new Email(this.address);
    }
}
