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
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;

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

        Thread.sleep(2000);

        TestRequests.createUser("test@email.com", "firstName", "lastName", mockMvc)
                .andExpect(status().isForbidden());
    }
}
