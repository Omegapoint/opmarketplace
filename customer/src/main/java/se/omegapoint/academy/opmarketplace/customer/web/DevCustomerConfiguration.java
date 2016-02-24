package se.omegapoint.academy.opmarketplace.customer.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing.EventRemotePublisherService;

@Configuration
@Profile("dev")
public class DevCustomerConfiguration {
    @Bean
    EventRemotePublisherService createEventRemotePublisherService(EventBus eventBus){
        return new EventRemotePublisherService(eventBus);
    }
}
