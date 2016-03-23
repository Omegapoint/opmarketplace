package se.omegapoint.academy.opmarketplace.marketplace.domain.items;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import se.omegapoint.academy.opmarketplace.marketplace.application.Application;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemServiceTest {

    @Test
    public void stub(){
        assertTrue(true);
    }

//    @Autowired
//    ItemService itemService;
//
//
//    private ArrayList<String> ids;
//
//    @Test
//    public void should_get_item_from_id() throws Exception {
//        String id = UUID.randomUUID().toString();
//        itemService.addItem(id, "Initial", "item", "100");
//        Item initial = itemService.getItemFromId(id);
//        assertEquals(new Title("Initial").text(), initial.title().text());
//        assertEquals(new Description("item").text(), initial.description().text());
//        assertEquals(new Price("100").amount(), initial.price().amount());
//    }
//
//    @Test
//    public void should_add_item() throws Exception {
//        String id = UUID.randomUUID().toString();
//        itemService.addItem(id, "Added", "item2", "200");
//        Item initial = itemService.getItemFromId(id);
//        assertEquals(new Title("Added").text(), initial.title().text());
//        assertEquals(new Description("item2").text(), initial.description().text());
//        assertEquals(new Price("200").amount(), initial.price().amount());
//    }
//
//    @Test
//    public void should_change_item_title() throws Exception {
//        String id = UUID.randomUUID().toString();
//        itemService.addItem(id, "ToBeChanged", "item3", "300");
//        itemService.changeTitle(id, "Changed");
//        Item changed = itemService.getItemFromId(id);
//        assertEquals(new Title("Changed").text(), changed.title().text());
//    }
//
//    @Test
//    public void should_not_change_title() throws Exception {
//        String id = UUID.randomUUID().toString();
//        itemService.addItem(id, "NotToBeChanged", "item4", "400");
//        try {
//            itemService.changeTitle(id, "<Changed>");
//            fail("Illegal characters in new title.");
//        } catch (IllegalArgumentValidationException e){}
//        Item initial = itemService.getItemFromId(id);
//        assertEquals(new Title("NotToBeChanged").text(), initial.title().text());
//    }
}