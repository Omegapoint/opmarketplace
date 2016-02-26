package se.omegapoint.accademy.opmarketplace.messageservice.domain.models;

import java.sql.Timestamp;

public class DomainEventModel {

    private String channel;
    private String aggregateId;
    private String aggregateName;
    private String eventType;
    private String eventData;
    private Timestamp time;

    public DomainEventModel(){}

    public DomainEventModel(String channel, String aggregateId, String aggregateName, String eventType, String eventData, Timestamp time) {
        this.channel = channel;
        this.aggregateId = aggregateId;
        this.aggregateName = aggregateName;
        this.eventType = eventType;
        this.eventData = eventData;
        this.time = time;
    }

    public String getChannel() {
        return channel;
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
