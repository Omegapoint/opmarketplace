package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateIdentity;
import se.omegapoint.academy.opmarketplace.customer.domain.Result;
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
    public Result<AggregateIdentity> domainObject() {
        try{
            return Result.success(new AggregateIdentity(this.id, this.aggregateName));
        } catch (IllegalArgumentValidationException e){
            return Result.error(e.getMessage());
        }
    }
}
