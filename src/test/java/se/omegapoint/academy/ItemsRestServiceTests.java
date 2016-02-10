package se.omegapoint.academy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.domain.items.Item;
import se.omegapoint.academy.domain.items.Title;
import se.omegapoint.academy.persistance.items.ItemRepositoryDomain;
import se.omegapoint.academy.web.Application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ItemsRestServiceTests {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ItemRepositoryDomain itemRepository;

    private List<UUID> ids;

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(wac).build();
        ids = new ArrayList<>(1);
        for (int i = 0; i < 1; i++) {
            ids.add(UUID.randomUUID());
        }
        itemRepository.addItem(new Item(ids.get(0), "First", "First item", "100", LocalDateTime.now().plusDays(7)));
    }

    @Test
    public void should_get_specific_item() throws Exception {
        mockMvc.perform(get("/items?id=" + ids.get(0) + "")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{id: " + ids.get(0).toString() + ", title: \"First\", description: \"First item\", price: \"100\"}"));
    }

    @Test
    public void should_add_item() throws Exception{
        mockMvc.perform(post("/items?title=Second&description=Second item&price=200")
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
        assert(1 == itemRepository.findByTitle(new Title("Second")).size());
        Item addedItem = itemRepository.findByTitle(new Title("Second")).get(0);
        assert("Second item".equals(addedItem.description()));
    }

    @Test
    public void should_not_add_item() throws Exception{
        mockMvc.perform(post("/items?title=<script>alert(Fail)</script>&description=Illegal title item&price=200")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }
}
