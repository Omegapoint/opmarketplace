package se.omegapoint.academy.opmarketplace.customer;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditDeposited;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.EntityMarker;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.*;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = AccountCreditDepositedJPA.class)
@Profile("credit")
public class DomainInjectionCreditConfiguration {

    @Bean
    AccountEventStore createAccountEventStore(AccountCreatedJPA accountCreatedRepository,
                                              AccountUserChangedJPA accountUserChangedRepository,
                                              AccountDeletedJPA accountDeletedRepository,
                                              AccountCreditDepositedJPA creditDepositRepository,
                                              AccountCreditWithdrawnJPA creditWithdrawnRepository,
                                              Boolean isVALIDATION){
        AccountEventStore eventStore = new AccountEventStore(accountCreatedRepository,
                accountUserChangedRepository,
                accountDeletedRepository,
                creditDepositRepository,
                creditWithdrawnRepository);

        eventStore.append(new AccountCreated(
                new Email("boost@market.com"),
                new User("boost", "market")
        ));

        return eventStore;
    }
}
