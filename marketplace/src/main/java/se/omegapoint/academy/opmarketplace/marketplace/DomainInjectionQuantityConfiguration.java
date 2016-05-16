package se.omegapoint.academy.opmarketplace.marketplace;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
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
@Profile("quantity")
public class DomainInjectionQuantityConfiguration {

    @Bean
    public ItemEventStore itemRepository(ItemCreatedJPARepository itemCreatedRepository,
                                         ItemChangedJPARepository itemChangedRepository,
                                         ItemOrderJPARepository itemOrderRepository,
                                         ItemOrderReverseJPARepository itemOrderReverseRepository,
                                         ItemReservedJPARepository itemReservedRepository,
                                         Boolean isVALIDATION){
        ItemEventStore eventStore = new ItemEventStore(itemCreatedRepository, itemChangedRepository, itemOrderRepository, itemOrderReverseRepository, itemReservedRepository);

        eventStore.append(
                new ItemCreated(
                    new Item(new Id(),
                        new Title("Item"),
                        new Description("Beautiful Item"),
                        new Credit(10),
                        new Quantity(10),
                        new Email("seller@market.com"))));
        return eventStore;
    }
}
