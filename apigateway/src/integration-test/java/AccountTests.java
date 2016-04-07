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
       createUser("test1@test.com", "fistName", "lastName")
               .andExpect(status().isOk())
               .andExpect(content().string(""));
    }

    @Test
    public void should_not_create_duplicate_account() throws Exception {
        createUser("test2@test.com", "fistName", "lastName")
                .andExpect(status().isOk());
        createUser("test2@test.com", "fistName", "lastName")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account already exists.\"}"));

    }

    @Test
    public void should_not_create_account_with_ill_formatted_email() throws Exception {
        createUser("@test.com", "fistName", "lastName")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Illegally formatted email.\"}"));
    }

    @Test
    public void should_get_correct_account() throws Exception {
        createUser("test3@test.com", "firstName", "lastName");

        String expectedResult = userJson("test3@test.com", "firstName", "lastName");
        getUser("test3@test.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.hasValue("test3@test.com")));
    }

    @Test
    public void should_not_get_non_existing_account() throws Exception {
        getUser("should_not_exist@test.com")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_not_get_account_with_ill_formatted_email() throws Exception {
        getUser("@test.com")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Illegally formatted email.\"}"));
    }

    @Test
    public void should_change_user() throws Exception {
        try {
            createUser("test4@test.com", "fistName", "lastName")
                    .andExpect(status().isOk());
        } catch (IllegalStateException e){}
        changeUser("test4@test.com", "changedFistName", "changedLastName")
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        getUser("test4@test.com")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.hasValue("test4@test.com")));
    }

    @Test
    public void should_delete_user() throws Exception {
        createUser("test5@test.com", "fistName", "lastName");
        deleteUser("test5@test.com")
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        getUser("test5@test.com")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_not_delete_non_existing_user() throws Exception {
        deleteUser("nonexistent@test.com")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"reason\":\"Account does not exist.\"}"));
    }

    @Test
    public void should_add_credits_to_account() throws Exception {
        createUser("toBe@rich.com", "ToBe", "Rich")
                .andExpect(status().isOk());
        addCredit("toBe@rich.com", 10)
                .andExpect(status().isOk());
        addCredit("toBe@rich.com", 20)
                .andExpect(status().isOk());
        getUser("toBe@rich.com")
                .andExpect(jsonPath("$.vault", Matchers.hasValue(30)));
    }

    @Test
    public void should_not_add_negative_credits_to_account() throws Exception {
        createUser("toRemain@poor.com", "ToBe", "Rich")
                .andExpect(status().isOk());
        addCredit("toRemain@poor.com", -1)
                .andExpect(status().isBadRequest());
        getUser("toRemain@poor.com")
                .andExpect(jsonPath("$.vault", Matchers.hasValue(0)));
    }

    /// HELPER METHODS ///
    private ResultActions deleteUser(String email) throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();


        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public ResultActions createUser(String email, String firstName, String lastName) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public ResultActions getUser(String email) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions changeUser(String email, String firstName, String lastName) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(put("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions addCredit(String email, int credit) throws Exception {
        String content = creditJson(email, credit);
        MvcResult mvcResult = mockMvc.perform(put("/accounts/credit")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private String userJson(String email, String firstName, String lastName) throws Exception {
        String content = "{" +
                    "\"email\":{" +
                        "\"address\":\"" + email + "\"" +
                    "}," +
                    "\"user\":{" +
                        "\"firstName\":\"" + firstName + "\"," +
                        "\"lastName\":\"" + lastName + "\"" +
                    "}" +
                "}";
        new ObjectMapper().readValue(content, AccountUserChangeRequestedDTO.class);
        return content;
    }

    private String creditJson(String email, int credit) throws Exception {
        String content = "{" +
                    "\"email\":{" +
                        "\"address\":\"" + email + "\"" +
                    "}," +
                    "\"credit\":{" +
                        "\"amount\":" + credit +
                    "}" +
                "}";
        new ObjectMapper().readValue(content, AccountCreditDepositRequestedDTO.class);
        return content;
    }
}
