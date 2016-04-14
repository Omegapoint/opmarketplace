package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.data_extraction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.marketplace.Application;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class ItemDataShortcutTest {

    @Autowired
    ItemEventStore eventStore;

    @Autowired
    ItemDataShortcut dataShortcut;

    @Test
    public void testGetMostImportantItemSince() {
        String seller = "gunilla@email.com";
        String buyer = "joakim@email.com";
        Id itemId1 = createItem("Title", "Description", 10, 10, seller);
        Id itemId2 = createItem("Title", "Description", 10, 10, seller);

        for (int i = 0; i < 5; i++) {
            orderItem(itemId1, seller, 1, 0, buyer);
        }

        for (int i = 0; i < 4; i++) {
            orderItem(itemId2, seller, 1, 0, buyer);
        }

        Item item = dataShortcut.getMostPopularItemSince(Timestamp.valueOf(LocalDateTime.now().minusDays(1))).get();
        Assert.assertEquals(itemId1, item.id());
    }

    private Id createItem(String title, String description, int credit, int quantity, String email) {
        Id itemId = new Id();

        PersistableEvent event = new ItemCreated(new Item(
                itemId,
                new Title(title),
                new Description(description),
                new Credit(credit),
                new Quantity(quantity),
                new Email(email)));

        eventStore.append(event);
        return itemId;
    }

    private void orderItem(Id itemId, String sellerEmail, int quantity, int credit, String buyerEmail) {
        PersistableEvent event = new ItemOrdered(new Order(
                itemId,
                new Email(sellerEmail),
                new Quantity(quantity),
                new Credit(credit),
                new Email(buyerEmail)));

        eventStore.append(event);
    }

}