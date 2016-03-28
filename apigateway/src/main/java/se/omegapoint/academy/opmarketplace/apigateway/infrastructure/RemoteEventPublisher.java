package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.OutgoingRemoteEvent;

public interface RemoteEventPublisher {
    void publish(OutgoingRemoteEvent outgoingRemoteEvent, String channel);
}
