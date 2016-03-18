package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequestedDTO implements JsonModel {

    public static final String TYPE = "AccountRequested";

    private EmailDTO email;
    private Timestamp timestamp;

    public AccountRequestedDTO() {timestamp = new Timestamp(System.currentTimeMillis());}

    public AccountRequestedDTO(EmailDTO email) {
        this.email = notNull(email);
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public EmailDTO getEmail() {
        return email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
