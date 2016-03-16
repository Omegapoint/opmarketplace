package se.omegapoint.academy.opmarketplace.apigateway.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Thread;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;
import se.omegapoint.academy.opmarketplace.apigateway.TestConfiguration;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserModel;


import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AccountGatewayTest {

    @Autowired
    TestConfiguration.TestPublisher testPublisher;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper json;

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(wac).build();
        json = new ObjectMapper();
    }

    @Test
    public void should_request_account_creation() throws Exception {
        EmailModel email = new EmailModel("test@test.com");
        UserModel user = new UserModel("testFirst", "testLast");
        AccountCreationRequestedModel request = new AccountCreationRequestedModel(email, user);

        String content = json.writeValueAsString(request);
        mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountCreationRequestedModel requestedModel = (AccountCreationRequestedModel) latestEvent;
        assertEquals(email.getAddress(), requestedModel.getEmail().getAddress());
        assertEquals(user.getFirstName(), requestedModel.getUser().getFirstName());
        assertEquals(user.getLastName(), requestedModel.getUser().getLastName());
    }

    @Test
    public void should_request_account_user_change() throws Exception {
        EmailModel email = new EmailModel("test@test.com");
        UserModel user = new UserModel("testFirst", "testLast");
        AccountUserChangeRequestedModel request = new AccountUserChangeRequestedModel(email, user);

        String content = json.writeValueAsString(request);
        mockMvc.perform(put("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountUserChangeRequestedModel requestedModel = (AccountUserChangeRequestedModel) latestEvent;
        assertEquals(email.getAddress(), requestedModel.getEmail().getAddress());
    }

    @Test
    public void should_request_account() throws Exception {
        EmailModel email = new EmailModel("test@test.com");

        mockMvc.perform(get("/accounts")
                .contentType(APPLICATION_JSON)
                .param("email", "test@test.com"));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountRequestedModel requestedModel = (AccountRequestedModel) latestEvent;
        assertEquals(email.getAddress(), requestedModel.getEmail().getAddress());
    }
}