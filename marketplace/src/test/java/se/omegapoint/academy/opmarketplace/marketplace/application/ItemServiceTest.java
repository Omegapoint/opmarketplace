package se.omegapoint.academy.opmarketplace.marketplace.application;

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
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.TestPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.ItemEventStore;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ItemServiceTest {

    @Autowired
    ItemRepository repository;

    @Autowired
    TestPublisher publisher;

    @Autowired
    ItemService itemService;

    @Test
    public void should_create_item() throws Exception {
        String requestId = "1";
        ItemCreationRequestedDTO request = new ItemCreationRequestedDTO(
                requestId,
                new TitleDTO("Item"),
                new DescriptionDTO("Desc"),
                new CreditDTO(1),
                new QuantityDTO(1),
                new EmailDTO("hej@hej.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemCreated itemCreated = (ItemCreated)publisher.getLatestEvent();
        assertEquals("Item", itemCreated.item().title().text());
    }

    @Test
    public void should_not_create_item_due_to_illegal_amount_in_price() throws Exception {
        String requestId = "1";
        ItemCreationRequestedDTO request = new ItemCreationRequestedDTO(
                requestId,
                new TitleDTO("Item"),
                new DescriptionDTO("Desc"),
                new CreditDTO(-1),
                new QuantityDTO(1),
                new EmailDTO("hej@hej.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemNotCreated itemNotCreated = (ItemNotCreated)publisher.getLatestEvent();
        assertEquals(Credit.ILLEGAL_FORMAT, itemNotCreated.reason());
    }

    @Test
    public void should_obtain_item() throws Exception {
        ItemCreated itemCreated = Item.createItem(new ItemCreationRequested(
                new Title("Obtainable"),
                new Description("Obtain"),
                new Credit(100),
                new Quantity(1),
                new Email("hej@hej.com")));
        repository.append(itemCreated);
        String itemId = itemCreated.item().id().toString();
        String requestId = "1";
        ItemRequestedDTO request = new ItemRequestedDTO(
                requestId,
                itemId);
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemObtained itemObtained = (ItemObtained)publisher.getLatestEvent();
        assertEquals("Obtainable", itemObtained.item().title().text());
    }

    @Test
    public void should_not_obtain_nonexistent_item() throws Exception {
        String itemId = UUID.randomUUID().toString();
        String requestId = "1";
        ItemRequestedDTO request = new ItemRequestedDTO(
                requestId,
                itemId);
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemNotObtained itemNotObtained = (ItemNotObtained)publisher.getLatestEvent();
        assertEquals(ItemEventStore.ITEM_DOES_NOT_EXIST, itemNotObtained.reason());
    }

    @Test
    public void should_find_three_matching_items(){
        String requestId = "1";

        ItemCreationRequested match1 = new ItemCreationRequested(
                new Title("Find"),
                new Description("Find"),
                new Credit(100),
                new Quantity(1),
                new Email("find@find.com"));
        ItemCreationRequested match2 = new ItemCreationRequested(
                new Title("What find"),
                new Description("no match"),
                new Credit(100),
                new Quantity(1),
                new Email("find@find.com"));
        ItemCreationRequested match3 = new ItemCreationRequested(
                new Title("No match"),
                new Description("Dude find"),
                new Credit(100),
                new Quantity(1),
                new Email("find@find.com"));
        ItemCreationRequested noMatch = new ItemCreationRequested(
                new Title("no match"),
                new Description("no match fin d"),
                new Credit(100),
                new Quantity(1),
                new Email("find@find.com"));

        repository.append(Item.createItem(match1));
        repository.append(Item.createItem(match2));
        repository.append(Item.createItem(match3));
        repository.append(Item.createItem(noMatch));

        ItemSearchRequestedDTO request = new ItemSearchRequestedDTO(
                requestId,
                "find");

        itemService.accept(reactor.bus.Event.wrap(request));
        ItemSearchCompleted searchCompleted = (ItemSearchCompleted) publisher.getLatestEvent();
        assertEquals(3, searchCompleted.items().size());
    }

    @Test
    public void should_change_item() throws Exception {
        String requestId = "1";
        Event request = new ItemCreationRequestedDTO(
                requestId,
                new TitleDTO("ToBeChanged"),
                new DescriptionDTO("ToBeChanged"),
                new CreditDTO(1),
                new QuantityDTO(1),
                new EmailDTO("NotToBe@changed.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemCreated itemCreated = (ItemCreated)publisher.getLatestEvent();

        request = new ItemChangeRequestedDTO(requestId,
                itemCreated.itemId().toString(),
                new TitleDTO("Changed"),
                new DescriptionDTO("Changed"),
                new CreditDTO(2),
                new QuantityDTO(2));
        itemService.accept(reactor.bus.Event.wrap(request));

        ItemChanged itemChanged = (ItemChanged)publisher.getLatestEvent();

        assertEquals(itemCreated.itemId(), itemChanged.itemId());
        assertEquals("Changed", itemChanged.item().title().text());
        assertEquals("Changed", itemChanged.item().description().text());
        assertEquals(2, itemChanged.item().price().amount());
        assertEquals(2, itemChanged.item().supply().amount());
        assertEquals("NotToBe@changed.com", itemChanged.item().seller().address());

        Event getRequest = new ItemRequestedDTO(
                requestId,
                itemCreated.itemId().toString());
        itemService.accept(reactor.bus.Event.wrap(getRequest));
        ItemObtained itemObtained = (ItemObtained)publisher.getLatestEvent();
        assertEquals("Changed", itemObtained.item().title().text());
    }

    @Test
    public void should_create_order() throws Exception {
        String requestId = "1";
        Event request = new ItemCreationRequestedDTO(
                requestId,
                new TitleDTO("ToBeOrdered"),
                new DescriptionDTO("To Be Ordered"),
                new CreditDTO(20),
                new QuantityDTO(5),
                new EmailDTO("sell@sell.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemCreated itemCreated = (ItemCreated)publisher.getLatestEvent();

        request = new ItemPurchaseRequestedDTO(
                requestId,
                itemCreated.itemId().toString(),
                new QuantityDTO(5),
                new EmailDTO("buy@buy.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemOrdered itemOrdered = (ItemOrdered)publisher.getLatestEvent();
        assertEquals(100, itemOrdered.price().amount());
        assertEquals(5, itemOrdered.quantity().amount());
        assertEquals("sell@sell.com", itemOrdered.sellerId().address());
        assertEquals("buy@buy.com", itemOrdered.buyerId().address());

        request = new ItemRequestedDTO(
                requestId,
                itemCreated.itemId().toString());
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemObtained itemObtained = (ItemObtained)publisher.getLatestEvent();
        assertEquals("ToBeOrdered", itemObtained.item().title().text());
        assertEquals(0, itemObtained.item().supply().amount());
    }

    @Test
    public void should_not_create_order_due_to_insufficient_supply() throws Exception {
        String requestId = "1";
        Event request = new ItemCreationRequestedDTO(
                requestId,
                new TitleDTO("NotToBeOrdered"),
                new DescriptionDTO("Not To Be Ordered"),
                new CreditDTO(20),
                new QuantityDTO(5),
                new EmailDTO("sell@sell.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemCreated itemCreated = (ItemCreated)publisher.getLatestEvent();

        request = new ItemPurchaseRequestedDTO(
                requestId,
                itemCreated.itemId().toString(),
                new QuantityDTO(6),
                new EmailDTO("buy@buy.com"));
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemNotOrdered itemNotOrdered = (ItemNotOrdered)publisher.getLatestEvent();
        assertEquals("Insufficient supply.", itemNotOrdered.reason());

        request = new ItemRequestedDTO(
                requestId,
                itemCreated.itemId().toString());
        itemService.accept(reactor.bus.Event.wrap(request));
        ItemObtained itemObtained = (ItemObtained)publisher.getLatestEvent();
        assertEquals("NotToBeOrdered", itemObtained.item().title().text());
        assertEquals(5, itemObtained.item().supply().amount());
    }
}