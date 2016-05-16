package se.omegapoint.academy.opmarketplace.marketplace.mitigation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.marketplace.Application;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;

import java.io.File;
import java.util.*;

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
        List<Id> similarItems = addSimilarItem();
        legitimateItems.addAll(similarItems);
        List<Id> maliciousItems = addMelicousItems(legitimateItems);
        List<Id> maliciousItemsWithChangedDescription = addMelicousItemsWithChangedDescription(legitimateItems);
        List<Id> maliciousItemsWithLoremDescription = addMelicousItemsWithLoremDescription(legitimateItems);

        FakeItemAnalyzer analyzer = new FakeItemAnalyzer();

        List<Id> itemIds = analyzer.getAllItemIds(itemJPARepository);
        Map<Id, Map<String, Integer>> wordFrequencies = analyzer.wordFrequencies(itemIds, itemRepository);
        List<Id> duplicates = analyzer.fakeItems(wordFrequencies, itemJPARepository);

        System.out.println("No duplicates: " + duplicates.size());
        System.out.printf("No duplicates of legitiamteItems: %d of %d%n", noItemsInList(legitimateItems, duplicates), legitimateItems.size());
        System.out.printf("No duplicates of maliciousItems: %d of %d%n", noItemsInList(maliciousItems, duplicates), maliciousItems.size());
        System.out.printf("No duplicates of maliciousItemsWithChangedDescription: %d of %d%n", noItemsInList(maliciousItemsWithChangedDescription, duplicates), maliciousItemsWithChangedDescription.size());
        System.out.printf("No duplicates of maliciousItemsWithLoremDescrpiton: %d of %d%n", noItemsInList(maliciousItemsWithLoremDescription, duplicates), maliciousItemsWithLoremDescription.size());

    }

    private long noItemsInList(List<Id> items, List<Id> list) {
        return items.stream().filter(list::contains).count();
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

    private List<Id> addSimilarItem() throws Exception {
        Scanner scanner = new Scanner(new File("src/main/resources/blocket_items_similar.txt"));

        String title = scanner.nextLine();
        String description = scanner.nextLine();

        Id id1 = new Id();
        eventStore.append(new ItemCreated(new Item(id1, new Title(title), new Description(description), new Credit(10), new Quantity(1), new Email("new@email.com"))));

        Id id2 = new Id();
        eventStore.append(new ItemCreated(new Item(id2, new Title(title), new Description(description), new Credit(10), new Quantity(1), new Email("legit@email.com"))));

        return Arrays.asList(id1, id2);
    }

    private List<Id> addMelicousItems(List<Id> legitimateItems) {
        List<Id> ids = new ArrayList<>();

        for (Id itemId : legitimateItems) {
            Item item = getItem(itemId);
            Id id = new Id();
            ids.add(id);
            eventStore.append(new ItemCreated(new Item(id,
                    item.title(),
                    item.description(),
                    item.price(),
                    item.supply(),
                    new Email("malicious@email.com"))));
        }

        return ids;
    }

    private List<Id> addMelicousItemsWithChangedDescription(List<Id> legitimateItems) {
        List<Id> ids = new ArrayList<>();

        for (Id itemId : legitimateItems) {
            Item item = getItem(itemId);
            Id id = new Id();
            ids.add(id);
            eventStore.append(new ItemCreated(new Item(id,
                    new Title(item.title().text() + " FAKE!"),
                    new Description(item.description().text() + " korvmoj"),
                    item.price(),
                    item.supply(),
                    new Email("malicious@email.com"))));
        }

        return ids;
    }

    private List<Id> addMelicousItemsWithLoremDescription(List<Id> legitimateItems) {
        List<Id> ids = new ArrayList<>();

        for (Id itemId : legitimateItems) {
            Item item = getItem(itemId);
            Id id = new Id();
            ids.add(id);
            eventStore.append(new ItemCreated(new Item(id,
                    new Title(item.title().text() + " FAKE!"),
                    new Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
                    item.price(),
                    item.supply(),
                    new Email("malicious@email.com"))));
        }

        return ids;
    }
}