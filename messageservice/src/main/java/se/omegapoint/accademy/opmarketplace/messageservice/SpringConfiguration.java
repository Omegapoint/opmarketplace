package se.omegapoint.accademy.opmarketplace.messageservice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;
import se.omegapoint.accademy.opmarketplace.messageservice.infrastructure.SubscriptionController;

@Configuration
public class SpringConfiguration {

    @Bean
    EventBus createEventBus() {
        return EventBus.create();
    }

    @Bean
    RuleEngine createRuleEngine(EventBus eventBus) {
        return new RuleEngine(eventBus);
    }

    @Bean
    SubscriptionController createSubscriptionReceiver(EventBus eventBus, RuleEngine ruleEngine) {
        return new SubscriptionController(eventBus, ruleEngine);
    }
}
