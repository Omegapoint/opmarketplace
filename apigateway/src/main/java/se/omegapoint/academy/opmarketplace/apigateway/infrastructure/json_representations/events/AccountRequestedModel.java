package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequestedModel implements JsonModel {

    public static final String TYPE = "AccountRequested";

    private EmailModel email;
    private Timestamp timestamp;

    public AccountRequestedModel() {timestamp = new Timestamp(System.currentTimeMillis());}

    public AccountRequestedModel(EmailModel email) {
        this.email = notNull(email);
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public EmailModel getEmail() {
        return email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
