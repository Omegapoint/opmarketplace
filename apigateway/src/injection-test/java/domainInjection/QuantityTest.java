package domainInjection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import json.Requests;
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
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountObtainedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.ItemSearchCompletedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.AccountDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuantityTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper json;

    public QuantityTest() throws IOException {
        json = new ObjectMapper();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void purchaseItem() throws Exception {
        String content = Requests.searchItems("Item", mockMvc).andReturn().getResponse().getContentAsString();
        ItemDTO searchResult = json.readValue(content.substring(1, content.length()-1), ItemDTO.class);
        Requests.purchaseItem(searchResult.id, "-5", "buyer@market.com", mockMvc).andExpect(status().isOk());

        AccountDTO buyer = json.readValue(Requests.getUser("buyer@market.com", mockMvc).andReturn().getResponse().getContentAsString(), AccountDTO.class);
        AccountDTO seller = json.readValue(Requests.getUser("seller@market.com", mockMvc).andReturn().getResponse().getContentAsString(), AccountDTO.class);

        System.out.println("Buyer vault: " + buyer.vault + " credit");
        System.out.println("Seller vault: " + seller.vault + " credit");
    }
}
