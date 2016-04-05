package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account_item;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.DTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO implements DTO {

    public final String address;

    @JsonCreator
    public EmailDTO(@JsonProperty("address") String address){
        this.address = notNull(address);
    }


}
