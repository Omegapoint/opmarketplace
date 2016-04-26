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
public class OwaspSqlStringsContextTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private SqlStringsContext sqlStringsContext;

    private ObjectWriter json;

    public OwaspSqlStringsContextTest() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
        sqlStringsContext = new SqlStringsContext();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void validateCustomer() throws Exception {

        CustomerLog log = new CustomerLog(
                new CreateAccountRequest(sqlStringsContext.values(), mockMvc),
                new ChangeUserRequest(sqlStringsContext.values(), mockMvc),
                new AddCreditRequest(sqlStringsContext.values(), mockMvc),
                new GetAccountRequest(sqlStringsContext.values(), mockMvc),
                new DeleteAccountRequest(sqlStringsContext.values(), mockMvc)
        );
        PrintWriter customerOutput = new PrintWriter(new File("src\\injection-test\\resources\\CustomerRequestsSqlContextLog.json"));
        customerOutput.write(json.writeValueAsString(log));
        customerOutput.close();
    }

    @Test
    public void validateMarketplace() throws Exception {
        MarketplaceLog log = new MarketplaceLog(
                new CreateItemRequest(sqlStringsContext.values(), mockMvc),
                new ChangeItemRequest(sqlStringsContext.values(), mockMvc),
                new GetItemRequest(sqlStringsContext.values(), mockMvc),
                new PurchaseItemRequest(sqlStringsContext.values(), mockMvc),
                new SearchItemRequest(sqlStringsContext.values(), mockMvc));
        PrintWriter marketplaceOutput = new PrintWriter(new File("src\\injection-test\\resources\\MarketplaceRequestsSqlContextLog.json"));
        marketplaceOutput.write(json.writeValueAsString(log));
        marketplaceOutput.close();
    }
}
