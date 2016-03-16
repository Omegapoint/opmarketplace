package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserChangedModel implements JsonModel {

    public static final String TYPE = "AccountUserChanged";

    private AggregateIdentityModel aggregateIdentity;

    public AccountUserChangedModel(){}

    public AggregateIdentityModel getAggregateIdentity() {
        return aggregateIdentity;
    }

}
