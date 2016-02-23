package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.DataObject;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.EventData;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.DomainEventEntity;

import java.time.LocalDateTime;

public class DomainEvent implements Comparable<DomainEvent>{
    private AggregateIdentity identity;
    private EventData data;
    private LocalDateTime time;

    public DomainEvent(String aggregateRootId, Class aggregateName, DataObject data) {
        identity = new AggregateIdentity(aggregateRootId, aggregateName.getSimpleName());
        time = LocalDateTime.now();
        this.data = new EventData(data);
    }

    /**
     * This constructor should only be used by repositories
     */
    public DomainEvent(DomainEventEntity eventEntity) {
        this.data = new EventData(eventEntity.getEventType(), eventEntity.getEventData());
        identity = new AggregateIdentity(eventEntity.getAggregateId(), eventEntity.getAggregateName());
        this.time = eventEntity.getTime();
    }

    public LocalDateTime time(){
        return time;
    }

    public String aggregateId(){
        return identity.id();
    }

    public String aggregateName(){
        return identity.aggregateName();
    }

    public String eventType(){
        return data.dataObjectType();
    }

    public String eventData() {
        return this.data.data();
    }

    @Override
    public int compareTo(DomainEvent other) {
        if (this.time().isAfter(other.time()))
            return 1;
        else if (this.time().isBefore(other.time()))
            return -1;
        return 0;
    }
}
