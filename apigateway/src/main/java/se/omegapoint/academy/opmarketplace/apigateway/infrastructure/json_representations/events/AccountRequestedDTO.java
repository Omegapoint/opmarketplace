package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequestedDTO implements JsonModel {

    public static final String TYPE = "AccountRequested";

    public final EmailDTO email;
    @JsonCreator
    public AccountRequestedDTO(@JsonProperty("email") EmailDTO email) {
        this.email = notNull(email);
    }
}
