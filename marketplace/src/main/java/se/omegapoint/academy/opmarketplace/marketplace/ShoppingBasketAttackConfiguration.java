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
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.*;

import java.io.FileNotFoundException;

@Configuration
@Profile("shoppingbasket")
public class ShoppingBasketAttackConfiguration {

    @Bean
    boolean isVALIDATION(){
        MainConfiguration.VALIDATION = false;
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
                                         ItemOrderReverseJPARepository itemOrderReverseRepository,
                                         ItemReservedJPARepository itemReservedRepository,
                                         boolean isValidation) throws FileNotFoundException {

        ItemEventStore eventStore = new ItemEventStore(itemCreatedRepository,
                itemChangedRepository,
                itemOrderRepository,
                itemOrderReverseRepository,
                itemReservedRepository);

        for (int i = 0; i < 50; i++) {
            eventStore.append(new ItemCreated(new Item(
                    new Id(getUUID(i)),
                    new Title("Shopping basket attack"),
                    new Description("Hodor hodor - hodor hodor hodor hodor hodor, hodor, hodor hodor."),
                    new Credit(1),
                    new Quantity(1),
                    new Email("seller@email.com")
            )));
        }

        return eventStore;
    }

    private String getUUID(int index) {
        String base = "00000000-0000-0000-0000-00000000000";
        String suffix = Integer.toString(index);
        return base.substring(0, base.length() - suffix.length()) + suffix;
    }

    @Bean
    SubscriberInitializer createSubscriberInitializer() {
        return new SubscriberInitializer();
    }
}
