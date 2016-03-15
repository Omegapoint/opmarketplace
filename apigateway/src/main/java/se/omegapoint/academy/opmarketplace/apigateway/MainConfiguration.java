package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisherService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.SubscriberInitializer;

@Configuration
public class MainConfiguration {

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }

}
