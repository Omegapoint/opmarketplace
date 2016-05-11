package se.omegapoint.academy.opmarketplace.marketplace.mitigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.marketplace.Application;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.EntityMarker;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.JpaRepositoryMarker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.apache.commons.lang.Validate.notNull;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("fakeitem")
public class FakeItemAnalyzerTest {

    @Autowired
    ItemCreatedJPARepository itemJPARepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemEventStore eventStore;

    @Test
    public void fakeItemTest() throws Exception {
        List<Id> legitimateItems = addLegitimateItems();

        FakeItemAnalyzer analyzer = new FakeItemAnalyzer();

        List<Id> itemIds = analyzer.getAllItemIds(itemJPARepository);
        Map<Id, Map<String, Integer>> wordFrequencies = analyzer.wordFrequencies(itemIds, itemRepository);
        List<String> duplicates = analyzer.fakeItems(wordFrequencies, itemJPARepository).stream()
                .map(this::getItem)
                .map(item -> item.title().text())
                .collect(Collectors.toList());

        System.out.println("No duplicates: " + duplicates.size());
        System.out.println(duplicates);
}

    private Item getItem(Id id) {
        return ((ItemObtained) itemRepository.item(id)).item();
    }

    private List<Id> addLegitimateItems() throws Exception {
        List<Id> ids = new ArrayList<>();
        Scanner scanner = new Scanner(new File("src/main/resources/blocket_items.txt"));

        while (scanner.hasNextLine()) {
            Id id = new Id();
            ids.add(id);
            eventStore.append(new ItemCreated(new Item(id,
                    new Title(scanner.nextLine()),
                    new Description(scanner.nextLine()),
                    new Credit(10),
                    new Quantity(1),
                    new Email("luke@tatooine.com"))));

            scanner.nextLine();
        }

        return ids;
    }
}