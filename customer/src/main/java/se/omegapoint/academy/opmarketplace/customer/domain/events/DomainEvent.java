package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.application.json_representations.DomainEventModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.DataObject;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.EventData;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.DomainEventEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class DomainEvent implements Comparable<DomainEvent>{
    private AggregateIdentity identity;
    private EventData data;
    private Timestamp time;

    public DomainEvent(String aggregateRootId, Class aggregateName, DataObject data) {
        identity = new AggregateIdentity(aggregateRootId, aggregateName.getSimpleName());
        time = Timestamp.valueOf(LocalDateTime.now());
        this.data = new EventData(data);
    }

    /**
     * This constructor should only be used by repositories
     * @param eventEntity a database model for domain events.
     */
    public DomainEvent(DomainEventEntity eventEntity) {
        notNull(eventEntity);
        this.data = new EventData(eventEntity.getEventType(), eventEntity.getEventData());
        identity = new AggregateIdentity(eventEntity.getAggregateId(), eventEntity.getAggregateName());
        this.time = eventEntity.getTime();
    }

    /**
     * This constructor should only be used by application services.
     * @param eventModel a data model for domain events.
     */
    public DomainEvent(DomainEventModel eventModel) {
        notNull(eventModel);
        this.data = new EventData(eventModel.getEventType(), eventModel.getEventData());
        identity = new AggregateIdentity(eventModel.getAggregateId(), eventModel.getAggregateName());
        this.time = eventModel.getTime();
    }

    public Timestamp time(){
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
        if (this.time().toLocalDateTime().isAfter(other.time().toLocalDateTime()))
            return 1;
        else if (this.time().toLocalDateTime().isBefore(other.time().toLocalDateTime()))
            return -1;
        return 0;
    }
}
