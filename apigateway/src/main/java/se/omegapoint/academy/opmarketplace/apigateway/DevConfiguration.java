package se.omegapoint.academy.opmarketplace.apigateway;

import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisherService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.SubscriberInitializer;

@Configuration
@Profile("dev")
public class DevConfiguration {

    @Bean(destroyMethod = "cleanup")
    public RemoteEventPublisherService createRemoteEventPublisher(){
        return new RemoteEventPublisherService(HttpAsyncClients.createDefault());
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
