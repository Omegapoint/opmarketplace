package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserNotChangedModel implements JsonModel {

    public static final String TYPE = "AccountUserNotChanged";

    private AggregateIdentityModel aggregateIdentity;
    private String reason;

    public AccountUserNotChangedModel(){}

    public AggregateIdentityModel getAggregateIdentity() {
        return aggregateIdentity;
    }

    public String getReason() {
        return reason;
    }
}
