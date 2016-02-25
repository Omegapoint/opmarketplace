package se.omegapoint.accademy.opmarketplace.messageservice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.analyzer.EventAnalyzer;
import se.omegapoint.accademy.opmarketplace.messageservice.services.EventPublisher;
import se.omegapoint.accademy.opmarketplace.messageservice.services.RuleEngine;

@Configuration
public class SpringConfiguration {

    @Bean
    EventBus createEventBus() {
        return EventBus.create();
    }

    @Bean
    EventAnalyzer createEventAnalyzer(EventBus eventBus) {
        return new EventAnalyzer(eventBus);
    }

    @Bean
    EventPublisher createEventPublisher(EventBus eventBus, RuleEngine ruleEngine) {
        return new EventPublisher(eventBus, ruleEngine);
    }

    @Bean
    RuleEngine createRuleEngine(EventBus eventBus) {
        return new RuleEngine(eventBus);
    }
}
