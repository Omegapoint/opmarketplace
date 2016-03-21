package se.omegapoint.academy.opmarketplace.apigateway.integration_tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebIntegrationTest
public class AccountTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
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
        createUser("test4@test.com", "fistName", "lastName")
                .andExpect(status().isOk());
        createUser("test3@test.com", "fistName", "lastName")
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
        createUser("test2@test.com", "firstName", "lastName");

        String expectedResult = userJson("test2@test.com", "firstName", "lastName");
        getUser("test2@test.com")
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
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
        createUser("test3@test.com", "fistName", "lastName");
        changeUser("test3@test.com", "changedFistName", "changedLastName")
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        String expectedResult = userJson("test3@test.com", "changedFistName", "changedLastName");
        getUser("test3@test.com")
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    public void should_delete_user() throws Exception {
        // TODO: 21/03/16 Implement
    }

    @Test
    public void should_not_delete_non_existing_user() throws Exception {
        // TODO: 21/03/16 Implement
    }

    private ResultActions createUser(String email, String firstName, String lastName) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        // TODO: 21/03/16 Maybe include this line?
        // mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private ResultActions getUser(String email) throws Exception {
        MvcResult getUserResult = mockMvc.perform(get("/accounts")
                .param("email", email))
                .andReturn();

        return mockMvc.perform(asyncDispatch(getUserResult));
    }

    private ResultActions changeUser(String email, String firstName, String lastName) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult changeUserResult = mockMvc.perform(put("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        return mockMvc.perform(asyncDispatch(changeUserResult));
    }

    private String userJson(String email, String firstName, String lastName) {
        return  "{" +
                    "\"email\":{" +
                        "\"address\":\"" + email + "\"" +
                    "}," +
                    "\"user\":{" +
                        "\"firstName\":\"" + firstName + "\"," +
                        "\"lastName\":\"" + lastName + "\"" +
                    "}" +
                "}";
    }

//    @Test
//    public void should_request_account_deletion() throws Exception {
//        mockMvc.perform(delete("/accounts")
//                .contentType(APPLICATION_JSON)
//                .param("email", "test@test.com"));
//        Event latestEvent = testPublisher.getLatestEvent();
//        assertNotNull(latestEvent);
//        AccountDeletionRequestedDTO requestedModel = (AccountDeletionRequestedDTO) latestEvent;
//        assertEquals("test@test.com" , requestedModel.email.address);
//    }
}
