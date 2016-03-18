package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDeletionRequestedModel implements JsonModel{

    public static final String TYPE = "AccountDeletionRequested";

    private EmailModel email;
    private Timestamp timestamp;

    public AccountDeletionRequestedModel() {timestamp = new Timestamp(System.currentTimeMillis());}

    public AccountDeletionRequestedModel(EmailModel email) {
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
