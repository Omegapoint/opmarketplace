package se.omegapoint.academy.opmarketplace.marketplace;


import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.SubscriberInitializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.JpaRepositoryMarker;

@Configuration
@Profile("di")
public class DomainInjectionConfiguration {

    @Bean
    Boolean isVALIDATION(){
        MainConfiguration.VALIDATION = false;
        return MainConfiguration.VALIDATION;
    }

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
