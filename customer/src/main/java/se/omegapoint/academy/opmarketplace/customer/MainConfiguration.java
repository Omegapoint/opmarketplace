package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.customer.application.AccountService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.EntityMarker;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;


@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = AccountCreatedJPA.class)
public class MainConfiguration {

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env, AccountService accountService) {
        EventBus eventBus = EventBus.create(env, Environment.THREAD_POOL);
        eventBus.on(Selectors.object("Account"), accountService);
        return eventBus;
    }


    @Bean
    AccountEventStore createAccountEventStore(AccountCreatedJPA accountCreatedRepository, AccountUserChangedJPA accountUserChangedRepository){
        return new AccountEventStore(accountCreatedRepository, accountUserChangedRepository);
    }
}
