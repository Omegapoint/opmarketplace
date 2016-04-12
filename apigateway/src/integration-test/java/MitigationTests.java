import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MitigationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void account_registration_is_turned_off_when_load_is_high() throws Exception {
        // Initial requests, should succeed.
        for (int i = 0; i < 10; i++) {
            String randomEmail = RandomStringUtils.randomAlphabetic(20) + "@email.com";
            TestRequests.createUser(randomEmail, "firstName", "lastName", mockMvc)
                    .andExpect(status().isOk());
        }

        // More request, should activate mitigation.
        for (int i = 0; i < 20; i++) {
            String randomEmail = RandomStringUtils.randomAlphabetic(20) + "@email.com";
            TestRequests.createUser(randomEmail, "firstName", "lastName", mockMvc);
        }

        TestRequests.createUser("test@email.com", "firstName", "lastName", mockMvc)
                .andExpect(status().isForbidden());

        Thread.sleep(10000);
    }

    @Test
    public void user_validation_is_activated_when_items_are_fetched_quickly() throws Exception {
        // Add two user and an item.
        String userOneEmail = "user_one@email.com"; String userTwoEmail = "user_two@email.com";
        TestRequests.createUser(userOneEmail, "firstName", "lastName", mockMvc)
                .andExpect(status().isOk());

        Thread.sleep(5000); // Make user "important"

        // User two is not "important"
        TestRequests.createUser(userTwoEmail, "firstName", "lastName", mockMvc)
                .andExpect(status().isOk());


        MvcResult mvcResult = TestRequests.createItem("Example title", "Example description", 10, 1, "seller@email.com", mockMvc)
                .andExpect(status().isOk())
                .andReturn();

        String itemId = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ItemDTO.class).id;

        // Initial requests, should succeed.
        for (int i = 0; i < 10; i++) {
            TestRequests.getItem(itemId, mockMvc)
                    .andExpect(status().isOk());
        }

        // More request, should activate mitigation.
        for (int i = 0; i < 20; i++) {
            TestRequests.getItem(itemId, mockMvc);
        }

        TestRequests.getItemAuth(itemId, userOneEmail, mockMvc)
                .andExpect(status().isOk());

        TestRequests.getItemAuth(itemId, userTwoEmail, mockMvc)
                .andExpect(status().isForbidden());

        TestRequests.getItem(itemId, mockMvc)
                .andExpect(status().isForbidden());

        Thread.sleep(10000);
    }
}
