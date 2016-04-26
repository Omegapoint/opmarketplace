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
import org.springframework.security.web.FilterChainProxy;
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
public class OwaspXssStringsTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private XssStrings xssStrings;

    private ObjectWriter json;

    public OwaspXssStringsTest() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
        xssStrings = new XssStrings();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void validateCustomer() throws Exception {

        CustomerLog log = new CustomerLog(
                new CreateAccountRequest(xssStrings.values(), mockMvc),
                new ChangeUserRequest(xssStrings.values(), mockMvc),
                new DepositCreditRequest(xssStrings.values(), mockMvc),
                new WithdrawCreditRequest(xssStrings.values(), mockMvc),
                new GetAccountRequest(xssStrings.values(), mockMvc),
                new DeleteAccountRequest(xssStrings.values(), mockMvc)
        );
        PrintWriter customerOutput = new PrintWriter(new File("src\\injection-test\\resources\\CustomerRequestsXSSLog.json"));
        customerOutput.write(json.writeValueAsString(log));
        customerOutput.close();
    }

    @Test
    public void validateMarketplace() throws Exception {
        MarketplaceLog log = new MarketplaceLog(
                new CreateItemRequest(xssStrings.values(), mockMvc),
                new ChangeItemRequest(xssStrings.values(), mockMvc),
                new GetItemRequest(xssStrings.values(), mockMvc),
                new PurchaseItemRequest(xssStrings.values(), mockMvc),
                new SearchItemRequest(xssStrings.values(), mockMvc));
        PrintWriter marketplaceOutput = new PrintWriter(new File("src\\injection-test\\resources\\MarketplaceRequestsXSSLog.json"));
        marketplaceOutput.write(json.writeValueAsString(log));
        marketplaceOutput.close();
    }
}
