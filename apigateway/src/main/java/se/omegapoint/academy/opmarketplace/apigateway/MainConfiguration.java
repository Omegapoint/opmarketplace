package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.apigateway.application.AccountGateway;
import se.omegapoint.academy.opmarketplace.apigateway.application.RuleEngine;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.EventReceiverService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.Router;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;

import java.util.HashMap;
import java.util.Timer;

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
    Router createRouter(EventBus eventBus){
        return new Router(eventBus, new Timer());
    }

    @Bean
    RuleEngine createRuleEngine() {
        return new RuleEngine();
    }
}
