package se.omegapoint.academy.opmarketplace.customer.application;

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
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.UserModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;

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

    @Before
    public void setUp() throws Exception{
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_add_account() throws Exception {
        String email = "test@test.com";
        String firstName = "testFirst";
        String lastName = "testLast";
        String content = new ObjectMapper().writeValueAsString(new AccountRequestedModel(new AccountRequested(new Email(email), new User(firstName, lastName))));
        mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_not_add_account_due_to_duplicate() throws Exception {
        String email = "block@block.com";
        String firstName = "blockFirst";
        String lastName = "blockLast";

        accountRepository.accept(Event.wrap(new AccountCreated(new Email(email), new User(firstName, lastName))));

        String content = new ObjectMapper().writeValueAsString(new AccountRequestedModel(new AccountRequested(new Email(email), new User(firstName, lastName))));
        mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void should_not_add_account_due_to_ill_formed_email() throws Exception {

        String content = "{\"email\":{\"address\":\"@broken.email\"},\"user\":{\"firstName\":\"mock\", \"lastName\":\"mock\"}}";
        mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void should_change_user() throws Exception {
        String email = "initial@initial.com";
        String firstName = "initial";
        String lastName = "initial";

        String newFirstName = "changed";
        String newLastName = "changed";

        String newUser = new ObjectMapper().writeValueAsString(new UserModel(new User(newFirstName, newLastName)));

        accountRepository.accept(Event.wrap(new AccountCreated(new Email(email), new User(firstName, lastName))));
        mockMvc.perform(put("/accounts?email=" + email)
                .contentType(APPLICATION_JSON)
                .content(newUser)
                .accept(APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void should_retrieve_account() throws Exception {
        String email = "retrieve@retrieve.com";
        String firstName = "retrieve";
        String lastName = "retrieve";

        accountRepository.accept(Event.wrap(new AccountCreated(new Email(email), new User(firstName, lastName))));
        mockMvc.perform(get("/accounts?email=" + email)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(new AccountModel(new Email(email), new User(firstName, lastName)))));
    }

    @Test
    public void should_not_retrieve_account_due_to_ill_formed_email() throws Exception {
        String email = "@noRetrieve.com";
        String firstName = "mock";
        String lastName = "mock";

        mockMvc.perform(get("/accounts?email=" + email)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }
}