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
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchWithValidation {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectWriter json;

    public SearchWithValidation() throws IOException {
        json = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void searchItems() throws Exception {
        int num_searches = 20;
        ArrayList<String> dictionary = new ArrayList<>(1000);
        Files.lines(Paths.get("src\\injection-test\\resources\\dictionary.txt")).forEach(dictionary::add);
        Random random = new Random();
        int responseTime = 0;
        for (int i = 0; i < num_searches; i++) {
            String query = "";
            for (int j = 0; j < 5; j++) {
                query = query + " " + dictionary.get(random.nextInt(1000));
            }
            long start = System.currentTimeMillis();
            Requests.searchItems(query, mockMvc)
                    .andExpect(status().isOk());
            responseTime += System.currentTimeMillis() - start;
        }
        System.out.println("Average response time: " + (responseTime / num_searches) + "ms");
    }
}
