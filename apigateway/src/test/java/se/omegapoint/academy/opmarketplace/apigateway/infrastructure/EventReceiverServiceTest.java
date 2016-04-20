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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountCreatedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountObtainedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.AccountUserChangedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.OutgoingRemoteEvent;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                eventBus.on(Selectors.object("test@test.com"), event -> {}).cancelAfterUse();

        AccountCreatedDTO model = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"" +
                "}", AccountCreatedDTO.class);

        String content = json.writeValueAsString(new OutgoingRemoteEvent(model));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(1000);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_obtained_confirmation() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object("test@test.com"), event -> {}).cancelAfterUse();

        AccountObtainedDTO model = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"account\":{" +
                "        \"email\":\"test@test.com\"," +
                "        \"user\":{" +
                "            \"firstName\":\"testFirst\"," +
                "            \"lastName\":\"testLast\"" +
                "        }," +
                "       \"vault\":" + 10 +
                "    }" +
                "}", AccountObtainedDTO.class);

        String content = json.writeValueAsString(new OutgoingRemoteEvent(model));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(1000);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_user_changed_confirmation() throws Exception {
        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object("test@test.com"), event -> {}).cancelAfterUse();

        AccountUserChangedDTO model = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"" +
                "}", AccountUserChangedDTO.class);

        String content = json.writeValueAsString(new OutgoingRemoteEvent(model));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(1000);
        assertTrue(registration.isCancelled());
    }

}
