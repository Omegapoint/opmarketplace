package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;


import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.OutgoingRemoteEvent;

public class TestPublisher implements RemoteEventPublisher {
    private Event lastestEvent;
    @Override
    public void publish(OutgoingRemoteEvent outgoingRemoteEvent) {
        lastestEvent = outgoingRemoteEvent.data;
    }

    public Event getLatestEvent(){
        return this.lastestEvent;
    }
}
