package se.omegapoint.accademy.opmarketplace.messageservice;

import java.sql.Timestamp;

public class DomainEventModel {

    private String aggregateId;
    private String aggregateName;
    private String eventType;
    private String eventData;
    private Timestamp time;

    public DomainEventModel(){}

    public DomainEventModel(String aggregateId, String aggregateName, String eventType, String eventData, Timestamp time) {
        this.aggregateId = aggregateId;
        this.aggregateName = aggregateName;
        this.eventType = eventType;
        this.eventData = eventData;
        this.time = time;
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

    public Timestamp getTime() {
        return time;
    }
}
