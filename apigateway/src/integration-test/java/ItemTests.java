import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }


    @Test
    public void should_create_an_item() throws Exception {
        createItem("Create", "Create", "100")
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void should_find_three_matches() throws Exception {
        createItem("Hej", "Hej", "100")
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        createItem("What hej", "no match", "100")
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        createItem("No match", "Dude hej", "100")
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        createItem("no match", "no match he j", "100")
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        searchItems("hej")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", Matchers.hasSize(3)));
    }

    private ResultActions createItem(String title, String description, String price) throws Exception {
        String content = itemCreationJson(title, description, price);
        MvcResult mvcResult = mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions searchItems(String query) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items/search/")
                .param("query", query)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String itemCreationJson(String title, String description, String price) {
        return  "{" +
                    "\"title\":{" +
                        "\"text\":\"" + title + "\"" +
                    "}," +
                    "\"description\":{" +
                        "\"text\":\"" + description + "\"" +
                    "}," +
                    "\"price\":{" +
                        "\"amount\":\"" + price + "\"" +
                    "}" +
                "}";
    }
}