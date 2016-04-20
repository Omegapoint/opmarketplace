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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectWriter json;

    public SearchTest() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void searchItemsValidation() throws Exception {
        int num_searches = 20;
        int num_search_terms = 5;
        List<String> dictionary = Dictionary.getWordList();
        int responseTime = Requests.getAverageResponseTimeForSearch(num_searches, num_search_terms, dictionary, mockMvc);
        System.out.println("Average response time: " + responseTime + "ms");
    }

    @Test
    public void searchItemsNoValidation() throws Exception {
        int num_searches = 20;
        int num_search_terms = 100;
        List<String> dictionary = Dictionary.getWordList();
        int responseTime = Requests.getAverageResponseTimeForSearch(num_searches, num_search_terms, dictionary, mockMvc);
        System.out.println("Average response time: " + responseTime + "ms");
    }
}
