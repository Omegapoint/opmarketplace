package se.omegapoint.academy.opmarketplace.customer;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive.SubscriberInitializer;

@Configuration
@Profile("dev")
public class DevConfiguration {

    @Bean(destroyMethod = "cleanup")
    EventRemotePublisherService createEventPublisher(){
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();
        return new EventRemotePublisherService(httpClient);
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
