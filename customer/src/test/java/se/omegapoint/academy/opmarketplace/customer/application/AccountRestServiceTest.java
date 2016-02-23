package se.omegapoint.academy.opmarketplace.customer.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import reactor.bus.Event;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.AccountEventStore;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@WebAppConfiguration
public class AccountRestServiceTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    AccountEventStore accountEventStore;

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
        mockMvc.perform(post("/accounts?email=" + email + "&first-eventType=" + firstName + "&last-eventType=" + lastName)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_change_first_name() throws Exception {
        String email = "initial@initial.com";
        String firstName = "initial";
        String lastName = "initial";

        String newFirstName = "changed";

        accountEventStore.accept(Event.wrap(new DomainEvent(email, Account.class, new AccountCreated(email, firstName, lastName))));
        mockMvc.perform(put("/accounts?email=" + email + "&first-eventType=" + newFirstName)
                .accept(APPLICATION_JSON))
                .andExpect(status().isAccepted());
        Thread.sleep(2000);
        assertEquals(newFirstName, accountEventStore.account(email).user().firstName());
    }
}