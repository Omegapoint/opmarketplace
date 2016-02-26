package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

public abstract class DomainEvent implements Comparable<DomainEvent> {
    public abstract Timestamp time();

    @Override
    public int compareTo(DomainEvent other){
        if (this.time().after(other.time()))
            return 1;
        if (this.time().before(other.time()))
            return -1;
        return 0;
    }
}
