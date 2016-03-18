package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.ReasonDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserNotChangedDTO implements JsonModel {

    public static final String TYPE = "AccountUserNotChanged";

    private EmailDTO email;
    private ReasonDTO reason;

    public AccountUserNotChangedDTO(){}

    public EmailDTO getEmail() {
        return email;
    }

    public ReasonDTO getReason() {
        return reason;
    }
}
