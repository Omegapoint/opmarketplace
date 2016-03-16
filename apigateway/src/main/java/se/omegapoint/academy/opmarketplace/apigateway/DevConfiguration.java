package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisherService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.SubscriberInitializer;

@Configuration
@Profile("dev")
public class DevConfiguration {

    @Bean
    public RemoteEventPublisher createRemoteEventPublisher(){
        return new RemoteEventPublisherService();
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
