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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        String content = "{\n" +
                "    \"email\":{\n" +
                "        \"address\":\"test@test.com\"\n" +
                "    },\n" +
                "    \"user\":{\n" +
                "        \"firstName\":\"testFirst\",\n" +
                "        \"lastName\":\"testLast\"\n" +
                "    }\n" +
                "}";
        mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountCreationRequestedModel requestedModel = (AccountCreationRequestedModel) latestEvent;
        assertEquals("test@test.com", requestedModel.getEmail().getAddress());
        assertEquals("testFirst", requestedModel.getUser().getFirstName());
        assertEquals("testLast", requestedModel.getUser().getLastName());
    }

    @Test
    public void should_request_account_user_change() throws Exception {
        String content = "{\n" +
                "    \"email\":{\n" +
                "        \"address\":\"test@test.com\"\n" +
                "    },\n" +
                "    \"user\":{\n" +
                "        \"firstName\":\"testFirst\",\n" +
                "        \"lastName\":\"testLast\"\n" +
                "    }\n" +
                "}";
        mockMvc.perform(put("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountUserChangeRequestedModel requestedModel = (AccountUserChangeRequestedModel) latestEvent;
        assertEquals("test@test.com", requestedModel.getEmail().getAddress());
    }

    @Test
    public void should_request_account() throws Exception {
        mockMvc.perform(get("/accounts")
                .contentType(APPLICATION_JSON)
                .param("email", "test@test.com"));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountRequestedModel requestedModel = (AccountRequestedModel) latestEvent;
        assertEquals("test@test.com" , requestedModel.getEmail().getAddress());
    }

    @Test
    public void should_request_account_deletion() throws Exception {
        mockMvc.perform(delete("/accounts")
                .contentType(APPLICATION_JSON)
                .param("email", "test@test.com"));
        JsonModel latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountDeletionRequestedModel requestedModel = (AccountDeletionRequestedModel) latestEvent;
        assertEquals("test@test.com" , requestedModel.getEmail().getAddress());
    }
}