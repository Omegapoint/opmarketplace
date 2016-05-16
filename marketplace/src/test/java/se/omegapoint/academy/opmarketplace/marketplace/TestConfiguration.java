package se.omegapoint.academy.opmarketplace.marketplace;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.TestPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.*;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryMarker.class)
@Profile("test")
public class TestConfiguration {

    @Bean
    Boolean isVALIDATION(){
        MainConfiguration.VALIDATION = true;
        return MainConfiguration.VALIDATION;
    }

    @Bean
    public TestPublisher createRemoteEventPublisher(){
        return new TestPublisher();
    }

    @Bean
    public ItemEventStore itemRepository(ItemCreatedJPARepository itemCreatedRepository,
                                         ItemChangedJPARepository itemChangedRepository,
                                         ItemOrderJPARepository itemOrderRepository,
                                         ItemOrderReverseJPARepository itemOrderReverseRepository,
                                         ItemReservedJPARepository itemReservedRepository,
                                         Boolean isVALIDATION){
        return new ItemEventStore(itemCreatedRepository, itemChangedRepository, itemOrderRepository, itemOrderReverseRepository, itemReservedRepository);
    }
}
