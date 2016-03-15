package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

public abstract class DomainEvent implements Comparable<DomainEvent> {

    public abstract Timestamp timestamp();

    @Override
    public int compareTo(DomainEvent other){
        if (this.timestamp().after(other.timestamp()))
            return 1;
        if (this.timestamp().before(other.timestamp()))
            return -1;
        return 0;
    }
}
