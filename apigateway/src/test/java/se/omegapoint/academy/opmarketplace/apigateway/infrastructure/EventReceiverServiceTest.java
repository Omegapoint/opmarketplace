package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.registry.Registration;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.ApigatewayApplication;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApigatewayApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class EventReceiverServiceTest {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper json = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_receive_account_creation_confirmation() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTCREATION.NAME + "test@test.com"), event -> {}).cancelAfterUse();

        AccountCreatedDTO model = json.readValue("{\n" +
                "    \"email\":{\n" +
                "        \"address\":\"test@test.com\"\n" +
                "    }\n" +
                "}", AccountCreatedDTO.class);

        String content = json.writeValueAsString(new RemoteEvent(model, AccountCreatedDTO.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_not_created_confirmation() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTCREATION.NAME + "@invalid.com"), event -> {}).cancelAfterUse();

        AccountNotCreatedDTO model = json.readValue("{\n" +
                "    \"email\":{\n" +
                "        \"address\":\"@invalid.com\"\n" +
                "    },\n" +
                "    \"reason\":\"Invalid Email\"\n" +
                "}", AccountNotCreatedDTO.class);

        String content = json.writeValueAsString(new RemoteEvent(model, AccountNotCreatedDTO.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_obtained() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTREQUEST.NAME + "test@test.com"), event -> {}).cancelAfterUse();

        AccountObtainedDTO model = json.readValue("{\n" +
                "    \"account\":{\n" +
                "        \"email\":{\n" +
                "            \"address\":\"test@test.com\"\n" +
                "        },\n" +
                "        \"user\":{\n" +
                "            \"firstName\":\"testFirst\",\n" +
                "            \"lastName\":\"testLast\"\n" +
                "        }\n" +
                "    }\n" +
                "}", AccountObtainedDTO.class);

        String content = json.writeValueAsString(new RemoteEvent(model, AccountObtainedDTO.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

//    @Test
//    public void should_receive_account_not_obtained() throws Exception {
//        Registration<Object, Consumer<? extends Event<?>>> registration =
//                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTREQUEST.NAME + "@invalid.com"), event -> {}).cancelAfterUse();
//
//        String content = "{" +
//                "   \"type\":\"AccountNotObtained\"," +
//                "   \"data\":{" +
//                "       \"email\":{" +
//                "           \"address\":\"@invalid.com\"" +
//                "       }," +
//                "       \"reason\":\"Invalid Email\"" +
//                "   }" +
//                "}";
//        mockMvc.perform(post("/event")
//                .contentType(APPLICATION_JSON)
//                .content(content)).andExpect(status().isOk());
//        Thread.sleep(50);
//        assertTrue(registration.isCancelled());
//    }

    @Test
    public void should_receive_account_user_changed_confirmation() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + "test@test.com"), event -> {}).cancelAfterUse();

        AccountUserChangedDTO model = json.readValue("{\n" +
                "    \"email\":{\n" +
                "        \"address\":\"test@test.com\"\n" +
                "    }\n" +
                "}", AccountUserChangedDTO.class);

        String content = json.writeValueAsString(new RemoteEvent(model, AccountUserChangedDTO.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

//    @Test
//    public void should_receive_account_user_not_changed_confirmation() throws Exception {
//        Registration<Object, Consumer<? extends Event<?>>> registration =
//                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + "@invalid.com"), event -> {}).cancelAfterUse();
//
//        AccountUserNotChangedDTO model = json.readValue("{\n" +
//                "    \"email\":{\n" +
//                "        \"address\":\"@invalid.com\"\n" +
//                "    },\n" +
//                "    \"reason\":\"Invalid Email\"\n" +
//                "}", AccountUserNotChangedDTO.class);
//
//        String content = json.writeValueAsString(new RemoteEvent(model, AccountUserNotChangedDTO.TYPE));
//        mockMvc.perform(post("/event")
//                .contentType(APPLICATION_JSON)
//                .content(content));
//        Thread.sleep(50);
//        assertTrue(registration.isCancelled());
//    }

//    @Test
//    public void should_receive_account_deleted_confirmation() throws Exception {
//        Registration<Object, Consumer<? extends Event<?>>> registration =
//                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + "test@test.com"), event -> {}).cancelAfterUse();
//
//        String content = "{" +
//                "   \"type\":\"AccountDeleted\"," +
//                "   \"data\":\"hej\""+
//                "}";
//        mockMvc.perform(post("/event")
//                .contentType(APPLICATION_JSON)
//                .content(content)).andExpect(status().isOk());
//        Thread.sleep(50);
//        assertTrue(registration.isCancelled());
//    }

//    @Test
//    public void should_receive_account_not_deleted_confirmation() throws Exception {
//        Registration<Object, Consumer<? extends Event<?>>> registration =
//                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + "@invalid.com"), event -> {}).cancelAfterUse();
//
//        AccountNotDeletedDTO model = json.readValue("{\n" +
//                "    \"email\":{\n" +
//                "        \"address\":\"@invalid.com\"\n" +
//                "    },\n" +
//                "    \"reason\":\"Invalid Email\"\n" +
//                "}", AccountNotDeletedDTO.class);
//
//        String content = json.writeValueAsString(new RemoteEvent(model, AccountNotDeletedDTO.TYPE));
//        mockMvc.perform(post("/event")
//                .contentType(APPLICATION_JSON)
//                .content(content));
//        Thread.sleep(50);
//        assertTrue(registration.isCancelled());
//    }

}
