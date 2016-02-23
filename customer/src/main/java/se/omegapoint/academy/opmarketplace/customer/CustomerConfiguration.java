package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountEventJPARepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.DomainEventEntity;

@Configuration
@EntityScan(basePackageClasses = DomainEventEntity.class)
@EnableJpaRepositories(basePackageClasses = AccountEventJPARepository.class)

public class CustomerConfiguration {

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
//        TODO Why isnt a new object created on each autowire.
            return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Bean
    AccountEventStore createAccountEventStore(EventBus eventBus, AccountEventJPARepository repository){
//        TODO Why isnt a new object created on each autowire.
        return new AccountEventStore(eventBus, repository);
    }
}
