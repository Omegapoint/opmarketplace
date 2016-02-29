package se.omegapoint.academy.opmarketplace.customer.application.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AggregateIdentityJsonModel {

    private String id;
    private String aggregateName;

    public AggregateIdentityJsonModel(AggregateIdentity aggregateIdentity) {
        notNull(aggregateIdentity);
        this.id = aggregateIdentity.id();
        this.aggregateName = aggregateIdentity.aggregateName();
    }

    public AggregateIdentityJsonModel(){}

    public String getId() {
        return id;
    }

    public String getAggregateName() {
        return aggregateName;
    }
}
