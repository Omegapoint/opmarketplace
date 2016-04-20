package se.omegapoint.academy.opmarketplace.marketplace;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.EventRemotePublisherService;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.event_publish_receive.SubscriberInitializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryMarker.class)
@Profile("search")
public class  DomainInjectionSearchConfiguration {

    @Bean
    public ItemEventStore itemRepository(ItemCreatedJPARepository itemCreatedRepository,
                                         ItemChangedJPARepository itemChangedRepository,
                                         ItemOrderJPARepository itemOrderRepository,
                                         ItemOrderReverseJPARepository itemOrderReverseRepository,
                                         Boolean isVALIDATION){
        ItemEventStore eventStore = new ItemEventStore(itemCreatedRepository, itemChangedRepository, itemOrderRepository, itemOrderReverseRepository);

        ArrayList<String> dictionary = new ArrayList<>(1000);
        try {
            Files.lines(Paths.get("src\\main\\resources\\dictionary.txt")).forEach(dictionary::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        for (int i = 0; i < 1000; i++){
            String title = "";
            String newString = title + dictionary.get(random.nextInt(1000));
            while (newString.length() < Title.MAX_LENGTH){
                title = newString;
                newString = title + " " + dictionary.get(random.nextInt(1000));
            }

            String description = "";
            newString = description + dictionary.get(random.nextInt(1000));
            while (newString.length() < Description.MAX_LENGTH){
                description = newString;
                newString = description + " " + dictionary.get(random.nextInt(1000));
            }

            eventStore.append(
                    new ItemCreated(
                        new Item(new Id(),
                            new Title(title),
                            new Description(description),
                            new Credit(10),
                            new Quantity(10),
                            new Email("seller@market.com"))));
        }
        return eventStore;
    }
}
