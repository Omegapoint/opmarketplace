package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionRequestedDTO implements JsonModel{

    public static final String TYPE = "AccountDeletionRequested";

    private EmailDTO email;
    private Timestamp timestamp;

    public AccountDeletionRequestedDTO() {timestamp = new Timestamp(System.currentTimeMillis());}

    public AccountDeletionRequestedDTO(EmailDTO email) {
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
