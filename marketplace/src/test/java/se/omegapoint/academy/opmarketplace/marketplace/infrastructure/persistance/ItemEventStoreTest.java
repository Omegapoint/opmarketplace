package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.omegapoint.academy.opmarketplace.marketplace.Application;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemSearchResult;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Description;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Price;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Title;

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
                new Price("100"));
        ItemCreationRequested match2 = new ItemCreationRequested(
                new Title("What hej"),
                new Description("no match"),
                new Price("100"));
        ItemCreationRequested match3 = new ItemCreationRequested(
                new Title("No match"),
                new Description("Dude hej"),
                new Price("100"));
        ItemCreationRequested noMatch = new ItemCreationRequested(
                new Title("no match"),
                new Description("no match he j"),
                new Price("100"));

        itemRepository.append(Item.createItem(match1));
        itemRepository.append(Item.createItem(match2));
        itemRepository.append(Item.createItem(match3));
        itemRepository.append(Item.createItem(noMatch));

        ItemSearchResult searchResult = itemRepository.findItems("hej");
        assertEquals(3, searchResult.items().size());
    }
}