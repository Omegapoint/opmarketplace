package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.TestPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountDeletionRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountUserChangeRequestedDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AccountGatewayTest {

    @Autowired
    TestPublisher testPublisher;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(wac).build();
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
        Event latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountCreationRequestedDTO requestedModel = (AccountCreationRequestedDTO) latestEvent;
        assertEquals("test@test.com", requestedModel.email.address);
        assertEquals("testFirst", requestedModel.user.firstName);
        assertEquals("testLast", requestedModel.user.lastName);
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
        Event latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountUserChangeRequestedDTO requestedModel = (AccountUserChangeRequestedDTO) latestEvent;
        assertEquals("test@test.com", requestedModel.email.address);
    }

    @Test
    public void should_request_account() throws Exception {
        mockMvc.perform(get("/accounts")
                .contentType(APPLICATION_JSON)
                .param("email", "test@test.com"));
        Event latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountRequestedDTO requestedModel = (AccountRequestedDTO) latestEvent;
        assertEquals("test@test.com" , requestedModel.email.address);
    }

    @Test
    public void should_request_account_deletion() throws Exception {
        mockMvc.perform(delete("/accounts")
                .contentType(APPLICATION_JSON)
                .param("email", "test@test.com"));
        Event latestEvent = testPublisher.getLatestEvent();
        assertNotNull(latestEvent);
        AccountDeletionRequestedDTO requestedModel = (AccountDeletionRequestedDTO) latestEvent;
        assertEquals("test@test.com" , requestedModel.email.address);
    }
}