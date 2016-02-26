package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.EntityMarker;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.DomainEventEntity;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = AccountCreatedJPA.class)
public class CustomerConfiguration {

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
    AccountEventStore createAccountEventStore(EventBus eventBus, AccountCreatedJPA accountCreatedRepository, AccountUserChangedJPA accountUserChangedRepository){
        return new AccountEventStore(eventBus, accountCreatedRepository, accountUserChangedRepository);
    }
}
