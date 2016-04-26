package se.omegapoint.academy.opmarketplace.marketplace;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.SubscriberInitializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemChangedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemOrderJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemOrderReverseJPARepository;

@Configuration
@Profile("cloudfoundry")
public class CloudFoundryConfiguration {

    @Bean
    boolean isVALIDATION(){
        MainConfiguration.VALIDATION = true;
        return MainConfiguration.VALIDATION;
    }

    @Bean(destroyMethod = "cleanup")
    EventRemotePublisherService createEventPublisher(){
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();
        return new EventRemotePublisherService(httpClient);
    }

    @Bean
    public ItemEventStore itemRepository(ItemCreatedJPARepository itemCreatedRepository,
                                         ItemChangedJPARepository itemChangedRepository,
                                         ItemOrderJPARepository itemOrderRepository,
                                         ItemOrderReverseJPARepository itemOrderReverseRepository){

        ItemEventStore eventStore = new ItemEventStore(itemCreatedRepository,
                itemChangedRepository,
                itemOrderRepository,
                itemOrderReverseRepository);

        eventStore.append(new ItemCreated(new Item(new Id("e161e16b-4295-4213-9c6e-8943003e5479"),
                new Title("This is a title"),
                new Description("This is a description"),
                new Credit(1),
                new Quantity(1000000),
                new Email("luke@tatooine.com"))));

        System.out.println("----- FINISHED ADDING DUMMY DATA -----");

        return eventStore;
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
