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
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.EmailModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.UserModel;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        EmailModel email = new EmailModel("test@test.com");

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTCREATION.NAME + email.getAddress()), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(new RemoteEvent(new AccountCreatedModel(email), AccountCreatedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_not_created_confirmation() throws Exception {
        EmailModel email = new EmailModel("@invalid.com");
        String reason = "Invalid Email";

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTCREATION.NAME + email.getAddress()), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(new RemoteEvent(new AccountNotCreatedModel(email, reason), AccountNotCreatedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_obtained() throws Exception {
        EmailModel email = new EmailModel("test@test.com");
        UserModel user = new UserModel("testFirst", "testLast");

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTREQUEST.NAME + email.getAddress()), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(new RemoteEvent(new AccountObtainedModel(new AccountModel(email, user)), AccountObtainedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_not_obtained() throws Exception {
        EmailModel email = new EmailModel("@invalid.com");
        String reason = "Invalid Email";

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTREQUEST.NAME + email.getAddress()), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(new RemoteEvent(new AccountNotObtainedModel(email, reason), AccountNotObtainedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_user_changed_confirmation() throws Exception {
        String id = "test@test.com";
        String aggregateName = "Account";

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + id), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(
                new RemoteEvent(new AccountUserChangedModel(new AggregateIdentityModel(id, aggregateName)), AccountUserChangedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

    @Test
    public void should_receive_account_user_not_changed_confirmation() throws Exception {
        String id = "@test.com";
        String aggregateName = "Account";
        String reason = "Invalid email";

        Registration<Object, Consumer<? extends Event<?>>> registration =
                eventBus.on(Selectors.object(Router.CHANNEL.ACCOUNTUSERCHANGE.NAME + id), event -> {}).cancelAfterUse();

        String content = json.writeValueAsString(
                new RemoteEvent(new AccountUserNotChangedModel(new AggregateIdentityModel(id, aggregateName), reason), AccountUserNotChangedModel.TYPE));
        mockMvc.perform(post("/event")
                .contentType(APPLICATION_JSON)
                .content(content));
        Thread.sleep(50);
        assertTrue(registration.isCancelled());
    }

}
