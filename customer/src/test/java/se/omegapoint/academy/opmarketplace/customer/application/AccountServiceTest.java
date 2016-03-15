package se.omegapoint.academy.opmarketplace.customer.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.TestConfiguration;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;

import java.sql.Timestamp;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    AccountEventStore accountRepository;

    MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestConfiguration.TestPublisher testPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception{
        testPublisher.clear();
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_create_account() throws Exception {
        String inputData = "{\"email\":{\"address\":\"test1@test.com\"}," +
                "\"user\":{\"firstName\":\"testFirst\", \"lastName\":\"testLast\"}," +
                "\"timestamp\":123456789}";
        AccountCreationRequestedModel model = objectMapper.readValue(inputData, AccountCreationRequestedModel.class);

        accountService.accept(Event.wrap(model));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_duplicate() throws Exception {
        String inputData = "{\"email\":{\"address\":\"test2@test.com\"}," +
                "\"user\":{\"firstName\":\"testFirst\", \"lastName\":\"testLast\"}," +
                "\"timestamp\":123456789}";

        AccountCreationRequestedModel model = objectMapper.readValue(inputData, AccountCreationRequestedModel.class);

        accountService.accept(Event.wrap(model));
        accountService.accept(Event.wrap(model));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_ill_formed_email() throws Exception {

        String inputData = "{\"email\":{\"address\":\"@broken.com\"}," +
                "\"user\":{\"firstName\":\"testFirst\", \"lastName\":\"testLast\"}," +
                "\"timestamp\":123456789}";

        AccountCreationRequestedModel model = objectMapper.readValue(inputData, AccountCreationRequestedModel.class);

        accountService.accept(Event.wrap(model));
        Assert.assertEquals(0, testPublisher.seenEvents(AccountCreated.class.getName()));
    }

    @Test
    public void should_change_user() throws Exception {
        Email email = new Email("initial@initial.com");
        User user = new User("initial", "initial");

        User newUser = new User("changed", "changed");

        String newUserModel = new ObjectMapper().writeValueAsString(new UserModel(newUser));

        accountRepository.append(Account.createAccount(new AccountCreationRequested(email, user, new Timestamp(1))));
        mockMvc.perform(put("/accounts?email=" + email.address())
                .contentType(APPLICATION_JSON)
                .content(newUserModel)
                .accept(APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void should_retrieve_account() throws Exception {
        Email email = new Email("retrieve@retrieve.com");
        User user = new User("retrieve", "retrieve");

        accountRepository.append(Account.createAccount(new AccountCreationRequested(email, user, new Timestamp(1))));
        mockMvc.perform(get("/accounts?email=" + email.address())
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(new AccountModel(email, user))));
    }

    @Test
    public void should_not_retrieve_account_due_to_ill_formed_email() throws Exception {
        String email = "@noRetrieve.com";

        mockMvc.perform(get("/accounts?email=" + email)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}