import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import json.customer.*;
import json.marketplace.*;
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OwaspLessThanVariationStrings {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private LessThanVariationStrings lessThanVariationStrings;

    private ObjectWriter json;

    public OwaspLessThanVariationStrings() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
        lessThanVariationStrings = new LessThanVariationStrings();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void validateCustomer() throws Exception {

        CustomerLog log = new CustomerLog(
                new CreateAccountRequest(lessThanVariationStrings.values(), mockMvc),
                new ChangeUserRequest(lessThanVariationStrings.values(), mockMvc),
                new AddCreditRequest(lessThanVariationStrings.values(), mockMvc),
                new GetAccountRequest(lessThanVariationStrings.values(), mockMvc),
                new DeleteAccountRequest(lessThanVariationStrings.values(), mockMvc)
        );
        PrintWriter customerOutput = new PrintWriter(new File("src\\injection-test\\resources\\CustomerRequestsLessThanVariationsLog.json"));
        customerOutput.write(json.writeValueAsString(log));
        customerOutput.close();
    }

    @Test
    public void validateMarketplace() throws Exception {
        MarketplaceLog log = new MarketplaceLog(
                new CreateItemRequest(lessThanVariationStrings.values(), mockMvc),
                new ChangeItemRequest(lessThanVariationStrings.values(), mockMvc),
                new GetItemRequest(lessThanVariationStrings.values(), mockMvc),
                new PurchaseItemRequest(lessThanVariationStrings.values(), mockMvc),
                new SearchItemRequest(lessThanVariationStrings.values(), mockMvc));
        PrintWriter marketplaceOutput = new PrintWriter(new File("src\\injection-test\\resources\\MarketplaceRequestsLessThanVariationsLog.json"));
        marketplaceOutput.write(json.writeValueAsString(log));
        marketplaceOutput.close();
    }
}
