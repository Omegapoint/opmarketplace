package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AggregateIdentityModel implements JsonModel {

    private String id;
    private String aggregateName;

    public AggregateIdentityModel(){}

    public String getId() {
        return id;
    }

    public String getAggregateName() {
        return aggregateName;
    }
}
