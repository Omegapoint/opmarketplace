package se.omegapoint.accademy.opmarketplace.eventanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.Analyzer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.data_adapters.UserAdapter;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.EventPublisher;

@Configuration
public class SpringConfiguration {

    @Bean
    EventBus createEventBus() {
        return EventBus.create();
    }

    @Bean
    UserAdapter createUserValidator() {
        return new UserAdapter();
    }

    @Bean
    Analyzer createAnalyzer(EventBus eventBus, UserAdapter userAdapter) {
        return new Analyzer(eventBus, userAdapter);
    }

    @Bean
    EventPublisher createEventPublisher(EventBus eventBus) {
        return new EventPublisher(eventBus);
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
