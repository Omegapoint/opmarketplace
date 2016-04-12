import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountCreditDepositRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountUserChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.UserDTO;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_create_an_account() throws Exception {
        TestRequests.createUser("test1@test.com", "fistName", "lastName", mockMvc)
               .andExpect(status().isOk())
               .andExpect(content().string(""));
    }

    @Test
    public void should_not_create_duplicate_account() throws Exception {
        TestRequests.createUser("test2@test.com", "fistName", "lastName", mockMvc)
                .andExpect(status().isOk());
        TestRequests.createUser("test2@test.com", "fistName", "lastName", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account already exists.\"}"));

    }

    @Test
    public void should_not_create_account_with_ill_formatted_email() throws Exception {
        TestRequests.createUser("@test.com", "fistName", "lastName", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Illegal Format: Illegally formatted email.\"}"));
    }

    @Test
    public void should_get_correct_account() throws Exception {
        TestRequests.createUser("test3@test.com", "firstName", "lastName", mockMvc);

        TestRequests.userJson("test3@test.com", "firstName", "lastName");

        TestRequests.getUser("test3@test.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test3@test.com"));
    }

    @Test
    public void should_not_get_non_existing_account() throws Exception {
        TestRequests.getUser("should_not_exist@test.com", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_not_get_account_with_ill_formatted_email() throws Exception {
        TestRequests.getUser("@test.com", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Illegal Format: Illegally formatted email.\"}"));
    }

    @Test
    public void should_change_user() throws Exception {
        try {
            TestRequests.createUser("test4@test.com", "fistName", "lastName", mockMvc)
                    .andExpect(status().isOk());
        } catch (IllegalStateException e){}
        TestRequests.changeUser("test4@test.com", "changedFistName", "changedLastName", mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        TestRequests.getUser("test4@test.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test4@test.com"));
    }

    @Test
    public void should_delete_user() throws Exception {
        TestRequests.createUser("test5@test.com", "fistName", "lastName", mockMvc);
        TestRequests.deleteUser("test5@test.com", mockMvc)
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        TestRequests.getUser("test5@test.com", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_not_delete_non_existing_user() throws Exception {
        TestRequests.deleteUser("nonexistent@test.com", mockMvc)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_add_credits_to_account() throws Exception {
        TestRequests.createUser("toBe@rich.com", "ToBe", "Rich", mockMvc)
                .andExpect(status().isOk());
        TestRequests.addCredit("toBe@rich.com", 10, mockMvc)
                .andExpect(status().isOk());
        TestRequests.addCredit("toBe@rich.com", 20, mockMvc)
                .andExpect(status().isOk());
        TestRequests.getUser("toBe@rich.com", mockMvc)
                .andExpect(jsonPath("$.vault").value(30));
    }

    @Test
    public void should_not_add_negative_credits_to_account() throws Exception {
        TestRequests.createUser("toRemain@poor.com", "ToBe", "Rich", mockMvc)
                .andExpect(status().isOk());
        TestRequests.addCredit("toRemain@poor.com", -1, mockMvc)
                .andExpect(status().isBadRequest());
        TestRequests.getUser("toRemain@poor.com", mockMvc)
                .andExpect(jsonPath("$.vault").value(0));
    }
}
