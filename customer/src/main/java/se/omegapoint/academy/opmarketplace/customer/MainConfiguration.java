package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.customer.application.AccountService;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.EntityMarker;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.*;


@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = AccountCreditDepositedJPA.class)
public class MainConfiguration {

    public static boolean VALIDATION = true;

    @Bean
    AccountService createAccountService(AccountRepository accountRepository, EventPublisher eventPublisher) {
        return new AccountService(accountRepository, eventPublisher);
    }

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
}
