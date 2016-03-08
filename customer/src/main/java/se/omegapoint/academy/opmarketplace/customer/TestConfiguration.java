package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;

@Configuration
@Profile("test")
public class TestConfiguration {
    @Bean
    public EventPublisher createEventPublisher(){
        return event -> {};
    }
}
