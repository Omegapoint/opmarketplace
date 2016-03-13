package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

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
