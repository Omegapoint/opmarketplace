package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisherService;

@Configuration
@Profile("dev")
public class DevConfiguration {

    @Bean
    public RemoteEventPublisher createRemoteEventPublisher(){
        return new RemoteEventPublisherService();
    }
}
