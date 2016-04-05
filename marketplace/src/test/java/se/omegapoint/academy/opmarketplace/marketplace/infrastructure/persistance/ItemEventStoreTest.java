package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.marketplace.Application;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemSearchCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class ItemEventStoreTest {

    @Autowired
    private ItemRepository itemRepository;
    @Test
    public void testFindItems() throws Exception {
        ItemCreationRequested match1 = new ItemCreationRequested(
                new Title("Hej"),
                new Description("Hej"),
                new Credit(100),
                new Quantity(1),
                new Email("hej@hej.com"));
        ItemCreationRequested match2 = new ItemCreationRequested(
                new Title("What hej"),
                new Description("no match"),
                new Credit(100),
                new Quantity(1),
                new Email("hej@hej.com"));
        ItemCreationRequested match3 = new ItemCreationRequested(
                new Title("No match"),
                new Description("Dude hej"),
                new Credit(100),
                new Quantity(1),
                new Email("hej@hej.com"));
        ItemCreationRequested noMatch = new ItemCreationRequested(
                new Title("no match"),
                new Description("no match he j"),
                new Credit(100),
                new Quantity(1),
                new Email("hej@hej.com"));

        itemRepository.append(Item.createItem(match1));
        itemRepository.append(Item.createItem(match2));
        itemRepository.append(Item.createItem(match3));
        itemRepository.append(Item.createItem(noMatch));

        ItemSearchCompleted searchResult = (ItemSearchCompleted) itemRepository.search("hej");
        assertEquals(3, searchResult.items().size());
    }
}