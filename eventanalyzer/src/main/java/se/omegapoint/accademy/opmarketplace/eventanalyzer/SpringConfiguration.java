package se.omegapoint.accademy.opmarketplace.eventanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.Analyzer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.EventPublisher;

@Configuration
public class SpringConfiguration {

    @Bean
    EventBus createEventBus() {
        return EventBus.create();
    }

    @Bean
    Analyzer createAnalyzer(EventBus eventBus) {
        return new Analyzer(eventBus);
    }

    @Bean
    EventPublisher createEventPublisher(EventBus eventBus) {
        return new EventPublisher(eventBus);
    }
}
