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
        TestRequests.createItem("Create", "Create", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Create"))
                .andExpect(jsonPath("$.description").value("Create"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.supply").value(1))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"));
    }

    @Test
    public void should_find_three_matches() throws Exception {
        TestRequests.createItem("Hej", "Hej", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk());
        TestRequests.createItem("What hej", "no match", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk());
        TestRequests.createItem("No match", "Dude hej", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk());
        TestRequests.createItem("no match", "no match he j", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk());
        TestRequests.searchItems("hej", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].supply").value(1));
    }

    @Test
    public void should_change_one_item() throws Exception {
        MvcResult result = TestRequests.createItem("ToBeChanged", "ToBeChanged", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ToBeChanged"))
                .andExpect(jsonPath("$.description").value("ToBeChanged"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.supply").value(1))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        TestRequests.changeItem(item.id, "Changed", "Changed", 200, 2, mockMvc)
                .andExpect(jsonPath("$.title").value("Changed"))
                .andExpect(jsonPath("$.description").value("Changed"))
                .andExpect(jsonPath("$.price").value(200))
                .andExpect(jsonPath("$.supply").value(2))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"));
        TestRequests.getItem(item.id, mockMvc)
                .andExpect(jsonPath("$.title").value("Changed"))
                .andExpect(jsonPath("$.description").value("Changed"))
                .andExpect(jsonPath("$.price").value(200))
                .andExpect(jsonPath("$.supply").value(2))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"));
    }

    @Test
    public void should_not_change_one_item_due_to_wrong_id() throws Exception {
        TestRequests.createItem("NotToBeChanged", "NotToBeChanged", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NotToBeChanged"))
                .andExpect(jsonPath("$.description").value("NotToBeChanged"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.supply").value(1))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"));
        TestRequests.changeItem(UUID.randomUUID().toString(), "Changed", "Changed", 200, 2, mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Item does not exist."));
    }

    @Test
    public void should_not_change_one_item_due_to_illegal_format_id() throws Exception {
        TestRequests.createItem("NotToBeChangedIllegalId", "NotToBeChangedIllegalId", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NotToBeChangedIllegalId"))
                .andExpect(jsonPath("$.description").value("NotToBeChangedIllegalId"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.supply").value(1))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"));
        TestRequests.changeItem("illegalId", "Changed", "Changed", 200, 2, mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Illegal Format: Id does not conform to UUID."));
    }

    @Test
    public void should_not_change_one_item_due_to_illegal_title() throws Exception {
        MvcResult result = TestRequests.createItem("NotToBeChanged", "NotToBeChanged", 100, 1, "hej@hej.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NotToBeChanged"))
                .andExpect(jsonPath("$.description").value("NotToBeChanged"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.supply").value(1))
                .andExpect(jsonPath("$.seller").value("hej@hej.com"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        TestRequests.changeItem(item.id, "<Changed", "Changed", 200, 2, mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").value("Illegal Format: Title can only contain letters, digits and spaces"));
    }

    @Test
    public void should_purchase_item() throws Exception {
        TestRequests.createUser("master@seller.com", "master", "seller", mockMvc)
                .andExpect(status().isOk());

        TestRequests.createUser("master@buyer.com", "master", "buyer", mockMvc)
                .andExpect(status().isOk());

        TestRequests.addCredit("master@buyer.com", 400, mockMvc)
                .andExpect(status().isOk());

        MvcResult result = TestRequests.createItem("ToBeBought", "ToBeBought", 100, 8, "master@seller.com", mockMvc)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ItemDTO item = new ObjectMapper().readValue(content, ItemDTO.class);
        TestRequests.purchaseItem(item.id, 4, "master@buyer.com", mockMvc)
                .andExpect(status().isOk());

        TestRequests.getUser("master@buyer.com", mockMvc)
                .andExpect(jsonPath("$.vault").value(0));

        TestRequests.getUser("master@seller.com", mockMvc)
                .andExpect(jsonPath("$.vault").value(400));
    }
}
