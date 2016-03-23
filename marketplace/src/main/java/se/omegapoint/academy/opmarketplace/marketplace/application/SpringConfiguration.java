package se.omegapoint.academy.opmarketplace.marketplace.application;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemRepositoryDomain;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.JpaRepositoryMarker;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryMarker.class)
public class SpringConfiguration {

    @Bean
    public ItemRepository itemRepository(ItemCreatedJPARepository itemCreatedJPARepository){
        ItemRepositoryDomain itemRepository = new ItemRepositoryDomain(itemCreatedJPARepository);
        return itemRepository;
    }


}
