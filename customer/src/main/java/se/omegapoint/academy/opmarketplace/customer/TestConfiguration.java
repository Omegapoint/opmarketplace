package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.*;

import java.util.HashMap;

@Configuration
@Profile("test")
public class TestConfiguration {


    @Bean
    AccountEventStore createAccountEventStore(AccountCreatedJPA accountCreatedRepository,
                                              AccountUserChangedJPA accountUserChangedRepository,
                                              AccountDeletedJPA accountDeletedRepository,
                                              AccountCreditDepositedJPA creditDepositRepository,
                                              AccountCreditWithdrawnJPA creditWithdrawnRepository){
        return new AccountEventStore(accountCreatedRepository,
                accountUserChangedRepository,
                accountDeletedRepository,
                creditDepositRepository,
                creditWithdrawnRepository);
    }

    @Bean
    public TestPublisher createTestPublisher() {
        return new TestPublisher();
    }

    public class TestPublisher implements EventPublisher {
        HashMap<String, Integer> seenDomainEvents = new HashMap<>();

        DomainEvent lastEvent;

        @Override
        public void publish(DomainEvent event, String requestId) {
            lastEvent = event;
            String eventName = event.getClass().getName();
            seenDomainEvents.merge(eventName, 1, (counter, one) -> counter + one);
        }

        public int seenEvents(String eventName) {
            return seenDomainEvents.containsKey(eventName) ? seenDomainEvents.get(eventName) : 0;
        }

        public void clear() {
            seenDomainEvents.clear();
        }

        public DomainEvent getLastEvent(){
            return lastEvent;
        }
    }
}
