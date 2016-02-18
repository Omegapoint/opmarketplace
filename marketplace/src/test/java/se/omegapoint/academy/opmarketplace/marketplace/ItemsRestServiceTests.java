package se.omegapoint.academy.opmarketplace.marketplace;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.items.Title;
import se.omegapoint.academy.opmarketplace.marketplace.web.Application;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemsRestServiceTests {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_get_specific_item() throws Exception {
        String id = UUID.randomUUID().toString();
        itemRepository.addItem(new Item(id, "First", "First item", "100", LocalDateTime.now().plusDays(7)));
        mockMvc.perform(get("/items?id=" + id + "")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{id: " + id + ", title: \"First\", description: \"First item\", price: \"100.00\"}"));
    }

    @Test
    public void should_add_item() throws Exception{
        mockMvc.perform(post("/items?title=Second&description=Second item&price=200")
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, itemRepository.findByTitle(new Title("Second")).size());
        Item addedItem = itemRepository.findByTitle(new Title("Second")).get(0);
        assertEquals("Second item", addedItem.description().text());
    }

    @Test
    public void should_not_add_item() throws Exception{
        mockMvc.perform(post("/items?title=<script>alert(Fail)</script>&description=Illegal title item&price=200")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void should_change_item() throws Exception{
        String id = UUID.randomUUID().toString();
        itemRepository.addItem(new Item(id, "ToBeChanged", "Change item", "200", LocalDateTime.now().plusDays(7)));
        mockMvc.perform(put("/items?id=" + id + "&title=Changed")
                .accept(APPLICATION_JSON))
                .andExpect(status().isAccepted());
        assertEquals("Changed", itemRepository.item(id).title().text());
    }
}
