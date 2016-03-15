package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AggregateIdentityModel implements JsonModel {

    private String id;
    private String aggregateName;

    public AggregateIdentityModel(AggregateIdentity aggregateIdentity) {
        notNull(aggregateIdentity);
        this.id = aggregateIdentity.id();
        this.aggregateName = aggregateIdentity.aggregateName();
    }

    public AggregateIdentityModel(){}

    public String getId() {
        return id;
    }

    public String getAggregateName() {
        return aggregateName;
    }

    @Override
    public AggregateIdentity domainObject() {
        return new AggregateIdentity(this.id, this.aggregateName);
    }
}
