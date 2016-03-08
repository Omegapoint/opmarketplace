package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing.EventRemotePublisherService;

@Configuration
@Profile("dev")
public class DevConfiguration {

    @Bean
    EventPublisher createEventPublisher(){
        return new EventRemotePublisherService();
    }
}
