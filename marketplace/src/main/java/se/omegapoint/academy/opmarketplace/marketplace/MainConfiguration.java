package se.omegapoint.academy.opmarketplace.marketplace;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import se.omegapoint.academy.opmarketplace.marketplace.application.ItemService;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.JpaRepositoryMarker;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryMarker.class)
public class MainConfiguration {

    @Bean
    ItemService createItemService(ItemRepository accountRepository, EventPublisher eventPublisher) {
        return new ItemService(accountRepository, eventPublisher);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env, ItemService itemService) {
        EventBus eventBus = EventBus.create(env, Environment.THREAD_POOL);
        eventBus.on(Selectors.object("Item"), itemService);
        return eventBus;
    }

    @Bean
    public ItemEventStore itemRepository(ItemCreatedJPARepository itemCreatedJPARepository){
        return new ItemEventStore(itemCreatedJPARepository);
    }
}
