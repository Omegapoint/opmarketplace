import com.fasterxml.jackson.databind.ObjectMapper;
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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountCreditDepositRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountUserChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemPurchaseRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        createItem("Create", "Create", 100, 1, "hej@hej.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.hasValue("Create")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("Create")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(100)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(1)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")));
    }

    @Test
    public void should_find_three_matches() throws Exception {
        createItem("Hej", "Hej", 100, 1, "hej@hej.com")
                .andExpect(status().isOk());
        createItem("What hej", "no match", 100, 1, "hej@hej.com")
                .andExpect(status().isOk());
        createItem("No match", "Dude hej", 100, 1, "hej@hej.com")
                .andExpect(status().isOk());
        createItem("no match", "no match he j", 100, 1, "hej@hej.com")
                .andExpect(status().isOk());
        searchItems("hej")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].supply", Matchers.hasValue(1)));
    }

    @Test
    public void should_change_one_item() throws Exception {
        MvcResult result = createItem("ToBeChanged", "ToBeChanged", 100, 1, "hej@hej.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.hasValue("ToBeChanged")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("ToBeChanged")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(100)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(1)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        changeItem(item.id, "Changed", "Changed", 200, 2)
                .andExpect(jsonPath("$.title", Matchers.hasValue("Changed")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("Changed")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(200)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(2)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")));
        getItem(item.id)
                .andExpect(jsonPath("$.title", Matchers.hasValue("Changed")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("Changed")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(200)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(2)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")));
    }

    @Test
    public void should_not_change_one_item_due_to_wrong_id_supplied() throws Exception {
        createItem("NotToBeChanged", "NotToBeChanged", 100, 1, "hej@hej.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.hasValue("NotToBeChanged")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("NotToBeChanged")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(100)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(1)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")));
        changeItem(UUID.randomUUID().toString(), "Changed", "Changed", 200, 2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.hasValue("Item does not exist.")));
    }

    @Test
    public void should_not_change_one_item_due_to_illegal_title() throws Exception {
        MvcResult result = createItem("NotToBeChanged", "NotToBeChanged", 100, 1, "hej@hej.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.hasValue("NotToBeChanged")))
                .andExpect(jsonPath("$.description", Matchers.hasValue("NotToBeChanged")))
                .andExpect(jsonPath("$.price", Matchers.hasValue(100)))
                .andExpect(jsonPath("$.supply", Matchers.hasValue(1)))
                .andExpect(jsonPath("$.seller", Matchers.hasValue("hej@hej.com")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        changeItem(item.id, "<Changed", "Changed", 200, 2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.hasValue("Title can only contain letters, digits and spaces")));
    }

    @Test
    public void should_purchase_item() throws Exception {
        createUser("master@seller.com", "master", "seller")
                .andExpect(status().isOk());

        createUser("master@buyer.com", "master", "buyer")
                .andExpect(status().isOk());

        addCredit("master@buyer.com", 400)
                .andExpect(status().isOk());

        MvcResult result = createItem("ToBeBought", "ToBeBought", 100, 8, "master@seller.com")
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        purchaseItem(item.id, 4, "master@buyer.com")
                .andExpect(status().isOk());

        getUser("master@buyer.com")
                .andExpect(jsonPath("$.vault", Matchers.hasValue(0)));

        getUser("master@seller.com")
                .andExpect(jsonPath("$.vault", Matchers.hasValue(400)));
    }

    private ResultActions createItem(String title, String description, int price, int quantity, String seller) throws Exception {
        String content = itemCreationJson(title, description, price, quantity, seller);
        MvcResult mvcResult = mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions getItem(String id) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items")
                .contentType(APPLICATION_JSON)
                .param("id", id)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String itemCreationJson(String title, String description, int price, int quantity, String seller) throws Exception {
        String content = "{" +
                    "\"title\":{" +
                        "\"text\":\"" + title + "\"" +
                    "}," +
                    "\"description\":{" +
                        "\"text\":\"" + description + "\"" +
                    "}," +
                    "\"price\":{" +
                        "\"amount\":" + price +
                    "}," +
                    "\"supply\":{" +
                        "\"amount\":" + quantity +
                    "}," +
                    "\"seller\":{" +
                        "\"address\":\"" + seller + "\"" +
                    "}" +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemCreationRequestedDTO.class);
        return content;
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

    private ResultActions changeItem(String id, String title, String description, int price, int quantity) throws Exception {
        String content = itemChangeJson(id, title, description, price, quantity);
        MvcResult mvcResult = mockMvc.perform(put("/items")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String itemChangeJson(String id, String title, String description, int price, int quantity) throws Exception {
        String content =  "{" +
                    "\"id\":\"" + id + "\"," +
                    "\"title\":{" +
                        "\"text\":\"" + title + "\"" +
                    "}," +
                    "\"description\":{" +
                        "\"text\":\"" + description + "\"" +
                    "}," +
                    "\"price\":{" +
                        "\"amount\":" + price +
                    "}," +
                    "\"supply\":{" +
                        "\"amount\":" + quantity +
                    "}" +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemChangeRequestedDTO.class);
        return content;
    }

    private ResultActions purchaseItem(String itemId, int quantity, String buyerId) throws Exception {
        String content = itemPurchaseRequestJson(itemId, quantity, buyerId);
        MvcResult mvcResult = mockMvc.perform(post("/items/purchase")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String itemPurchaseRequestJson(String itemId, int quantity, String buyerId) throws Exception {
        String content =
                "{" +
                    "\"itemId\":\"" + itemId + "\"," +
                    "\"quantity\":{" +
                        "\"amount\":" + quantity +
                    "}," +
                    "\"buyerId\":{" +
                        "\"address\":\"" + buyerId + "\"" +
                    "}" +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemPurchaseRequestedDTO.class);
        return content;
    }

    public ResultActions createUser(String email, String firstName, String lastName) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public ResultActions getUser(String email) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions addCredit(String email, int credit) throws Exception {
        String content = creditJson(email, credit);
        MvcResult mvcResult = mockMvc.perform(put("/accounts/credit")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String creditJson(String email, int credit) throws Exception {
        String content =
                "{" +
                    "\"email\":{" +
                        "\"address\":\"" + email + "\"" +
                    "}," +
                    "\"credit\":{" +
                        "\"amount\":" + credit +
                    "}" +
                "}";
        new ObjectMapper().readValue(content, AccountCreditDepositRequestedDTO.class);
        return content;
    }

    private String userJson(String email, String firstName, String lastName) throws Exception {
        String content =
                "{" +
                    "\"email\":{" +
                        "\"address\":\"" + email + "\"" +
                    "}," +
                    "\"user\":{" +
                        "\"firstName\":\"" + firstName + "\"," +
                        "\"lastName\":\"" + lastName + "\"" +
                    "}" +
                "}";
        new ObjectMapper().readValue(content, AccountUserChangeRequestedDTO.class);
        return content;
    }
}
