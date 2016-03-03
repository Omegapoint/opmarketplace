package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    AccountEventStore createAccountEventStore(EventBus eventBus, AccountEventPublisher publisher, AccountCreatedJPA accountCreatedRepository, AccountUserChangedJPA accountUserChangedRepository){
        return new AccountEventStore(eventBus, publisher, accountCreatedRepository, accountUserChangedRepository);
    }
}
