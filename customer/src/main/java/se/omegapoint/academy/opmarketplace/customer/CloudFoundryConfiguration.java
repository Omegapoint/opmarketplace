package se.omegapoint.academy.opmarketplace.customer;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publish_receive.SubscriberInitializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.*;

@Configuration
@Profile("cloudfoundry")
public class CloudFoundryConfiguration {

    @Bean(destroyMethod = "cleanup")
    EventRemotePublisherService createEventPublisher(){
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();
        return new EventRemotePublisherService(httpClient);
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }

    @Bean
    AccountEventStore createAccountEventStore(AccountCreatedJPA accountCreatedRepository,
                                              AccountUserChangedJPA accountUserChangedRepository,
                                              AccountDeletedJPA accountDeletedRepository,
                                              AccountCreditDepositedJPA creditDepositRepository,
                                              AccountCreditWithdrawnJPA creditWithdrawnRepository){
        AccountEventStore eventStore = new AccountEventStore(accountCreatedRepository,
                accountUserChangedRepository,
                accountDeletedRepository,
                creditDepositRepository,
                creditWithdrawnRepository);

        // Create a couple of users
        String[] users = {"sofie@email.com", "jakob@email.com", "anna@email.com", "emil@email.com"};
        for (String email: users) {
            eventStore.append(new AccountCreated(new Email(email), new User("firstName", "lastName")));
        }

        System.out.println("----- FINISHED ADDING DUMMY DATA -----");

        return eventStore;
    }
}
