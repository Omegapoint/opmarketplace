import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import json.*;
import org.junit.After;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private PrintWriter accountOutput;

    private ObjectWriter json;

    public OwaspSQLStrings() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
        sqlStrings = new SQLStrings(new File("src\\injection-test\\resources\\SQLStrings.txt"));
        accountOutput = new PrintWriter(new File("src\\injection-test\\resources\\AccountRequestsInjectionLog.json"));
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() throws Exception {
        accountOutput.close();
    }

    @Test
    public void validateAccount() throws Exception {
        AccountLog log = new AccountLog(
                new CreateAccountRequest(sqlStrings.values(), mockMvc),
                new ChangeUserRequest(sqlStrings.values(), mockMvc),
                new AddCreditRequest(sqlStrings.values(), mockMvc),
                new GetAccountRequest(sqlStrings.values(), mockMvc),
                new DeleteAccountRequest(sqlStrings.values(), mockMvc)
        );
        accountOutput.write(json.writeValueAsString(log));
    }


}
