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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Configuration
@Profile("fakeitem")
public class FakeItemConfiguration {

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
                                         ItemOrderReverseJPARepository itemOrderReverseRepository) throws FileNotFoundException {

        MainConfiguration.VALIDATION = false;

        ItemEventStore eventStore = new ItemEventStore(itemCreatedRepository,
                itemChangedRepository,
                itemOrderRepository,
                itemOrderReverseRepository);

        System.out.println("Working directory = " + System.getProperty("user.dir"));
        Scanner scanner = new Scanner(new File("src/main/resources/blocket_items.txt"));

        while (scanner.hasNextLine()) {
            eventStore.append(new ItemCreated(new Item(new Id(),
                    new Title(scanner.nextLine()),
                    new Description(scanner.nextLine()),
                    new Credit(10),
                    new Quantity(1),
                    new Email("luke@tatooine.com"))));

            scanner.nextLine();
        }

        System.out.println("----- FINISHED ADDING DUMMY DATA -----");

        return eventStore;
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
