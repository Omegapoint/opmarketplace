package se.omegapoint.academy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.ItemRepository;
import se.omegapoint.academy.persistance.items.ItemRepositoryDomain;
import se.omegapoint.academy.domain.items.ItemService;
import se.omegapoint.academy.persistance.EntityMarker;
import se.omegapoint.academy.persistance.JpaRepositoryMarker;
import se.omegapoint.academy.persistance.items.ItemJPARepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public ItemRepositoryDomain itemRepository(){
        ItemRepositoryDomain itemRepository = new ItemRepositoryDomain(itemJPARepository);
        return itemRepository;
    }

    @PostConstruct
    public void createTestData(){
        itemRepository.addItem(new Item(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "First", "First item", 100, LocalDateTime.now().plusDays(7)));
    }


}
