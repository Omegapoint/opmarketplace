package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;

import java.util.HashMap;
import java.util.HashSet;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public TestPublisher createTestPublisher() {
        return new TestPublisher();
    }

    public class TestPublisher implements EventPublisher {
        HashMap<String, Integer> seenDomainEvents = new HashMap<>();

        @Override
        public void publish(AccountCreated event) {
            String eventName = event.getClass().getName();
            seenDomainEvents.merge(eventName, 1, (counter, one) -> counter + one);
        }

        @Override
        public void publish(AccountNotCreated event) {
            String eventName = event.getClass().getName();
            seenDomainEvents.merge(eventName, 1, (counter, one) -> counter + one);
        }

        @Override
        public void publish(AccountObtained event) {
            String eventName = event.getClass().getName();
            seenDomainEvents.merge(eventName, 1, (counter, one) -> counter + one);
        }

        @Override
        public void publish(AccountUserChanged event) {
            String eventName = event.getClass().getName();
            seenDomainEvents.merge(eventName, 1, (counter, one) -> counter + one);
        }

        public int seenEvents(String eventName) {
            return seenDomainEvents.containsKey(eventName) ? seenDomainEvents.get(eventName) : 0;
        }

        public void clear() {
            seenDomainEvents.clear();
        }
    }
}
