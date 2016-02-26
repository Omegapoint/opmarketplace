package se.omegapoint.academy.opmarketplace.customer.domain.events;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class AggregateIdentity {

    private final String id;
    private final String aggregateName;

    public AggregateIdentity(String id, String aggregateName) {
        notBlank(id);
        notBlank(aggregateName);
        this.id = id;
        this.aggregateName = aggregateName;
    }

    public String id() {
        return id;
    }

    public String aggregateName() {
        return aggregateName;
    }
}
