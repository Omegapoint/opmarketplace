package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.RemoteEvent;

public interface RemoteEventPublisher {
    void publish(RemoteEvent remoteEvent);
}
