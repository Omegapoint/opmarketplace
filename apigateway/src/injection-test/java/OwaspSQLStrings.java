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

import java.io.*;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OwaspSQLStrings {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private SQLStrings sqlStrings;

    private PrintWriter customerOutput;
    private PrintWriter marketplaceOutput;

    private ObjectWriter json;

    public OwaspSQLStrings() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
        sqlStrings = new SQLStrings(new File("src\\injection-test\\resources\\SQLStrings.txt"));
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void validateCustomer() throws Exception {

        CustomerLog log = new CustomerLog(
                new CreateAccountRequest(sqlStrings.values(), mockMvc),
                new ChangeUserRequest(sqlStrings.values(), mockMvc),
                new AddCreditRequest(sqlStrings.values(), mockMvc),
                new GetAccountRequest(sqlStrings.values(), mockMvc),
                new DeleteAccountRequest(sqlStrings.values(), mockMvc)
        );
        customerOutput = new PrintWriter(new File("src\\injection-test\\resources\\CustomerRequestsInjectionLog.json"));
        customerOutput.write(json.writeValueAsString(log));
        customerOutput.close();
    }

    @Test
    public void validateMarketplace() throws Exception {
        MarketplaceLog log = new MarketplaceLog(
                new CreateItemRequest(sqlStrings.values(), mockMvc),
                new ChangeItemRequest(sqlStrings.values(), mockMvc),
                new GetItemRequest(sqlStrings.values(), mockMvc),
                new PurchaseItemRequest(sqlStrings.values(), mockMvc),
                new SearchItemRequest(sqlStrings.values(), mockMvc));
        marketplaceOutput = new PrintWriter(new File("src\\injection-test\\resources\\MarketplaceRequestsInjectionLog.json"));
        marketplaceOutput.write(json.writeValueAsString(log));
        marketplaceOutput.close();
    }


}
