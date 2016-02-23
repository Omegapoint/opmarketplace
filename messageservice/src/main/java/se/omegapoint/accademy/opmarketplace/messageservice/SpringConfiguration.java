package se.omegapoint.accademy.opmarketplace.messageservice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.bus.EventBus;

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
}
