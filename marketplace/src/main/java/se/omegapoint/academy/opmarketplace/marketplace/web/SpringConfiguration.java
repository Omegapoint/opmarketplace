package se.omegapoint.academy.opmarketplace.marketplace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.ItemService;
import se.omegapoint.academy.opmarketplace.marketplace.persistance.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.persistance.items.ItemJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.persistance.items.ItemRepositoryDomain;
import se.omegapoint.academy.opmarketplace.marketplace.persistance.JpaRepositoryMarker;

@Configuration
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryMarker.class)
public class SpringConfiguration {

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ItemRepository itemRepository;

    @Bean
    public ItemService itemService() {
        ItemService itemService = new ItemService(itemRepository);
        return itemService;
    }

    @Bean
    public ItemRepository itemRepository(){
        ItemRepositoryDomain itemRepository = new ItemRepositoryDomain(itemJPARepository);
        return itemRepository;
    }


}
