package se.omegapoint.academy.opmarketplace.customer.domain.events;

import java.sql.Timestamp;

public class RemoteEvent {
    public final String type;
    public final Timestamp time;
    public final String data;

//    public static RemoteEvent wrap(DomainEvent domainEvent) {
//        return new RemoteEvent(domainEvent.getClass().getSimpleName(), domainEvent.json());
//
//    }

    private RemoteEvent(String type, String data) {
        this.type = type;
        time = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }
}
