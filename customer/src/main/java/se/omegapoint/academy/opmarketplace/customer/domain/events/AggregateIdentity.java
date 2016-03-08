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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregateIdentity that = (AggregateIdentity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return aggregateName != null ? aggregateName.equals(that.aggregateName) : that.aggregateName == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (aggregateName != null ? aggregateName.hashCode() : 0);
        return result;
    }
}
