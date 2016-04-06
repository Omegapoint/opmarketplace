package se.omegapoint.accademy.opmarketplace.eventanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.Analyzer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.control_mechanisms.UserValidator;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.EventPublisher;

@Configuration
public class SpringConfiguration {

    @Bean
    EventBus createEventBus() {
        return EventBus.create();
    }

    @Bean
    UserValidator createUserValidator() {
        return new UserValidator();
    }

    @Bean
    Analyzer createAnalyzer(EventBus eventBus, UserValidator userValidator) {
        return new Analyzer(eventBus, userValidator);
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
