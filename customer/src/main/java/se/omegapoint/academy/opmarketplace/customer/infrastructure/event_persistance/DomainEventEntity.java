package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class DomainEventEntity{

    @Id
    @GeneratedValue
    private Long id;
    private String aggregateId;
    private String aggregateName;
    private String eventType;
    private String eventData;
    private Timestamp time;


    public DomainEventEntity(){}

    public DomainEventEntity(String aggregateId, String aggregateName, String eventType, String eventData, LocalDateTime time) {
        this.aggregateId = aggregateId;
        this.aggregateName = aggregateName;
        this.eventType = eventType;
        this.eventData = eventData;
        this.time = Timestamp.valueOf(time);
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateName() {
        return aggregateName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public LocalDateTime getTime() {
        return time.toLocalDateTime();
    }
}
