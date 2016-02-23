package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.AccountEventStore;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
public class AccountTest {

    @Autowired
    EventBus eventBus;

    @Autowired
    AccountEventStore eventStore;

    @Test
    public void should_create_account(){
        String email = "create@create.com";
        String firstName = "createFirst";
        String lastName = "createLast";
        Account account = new Account(email, firstName, lastName, new AccountEventPublisherService(eventBus));
        assertEquals(email, account.email().address());
        assertEquals(email, account.id());
        assertEquals(firstName, account.user().firstName());
        assertEquals(lastName, account.user().lastName());
    }
    @Test
    public void should_change_last_name_to_z() throws IOException, InterruptedException {
        String email = "change@change.com";
        String firstName = "initial";
        String lastName = "initial";
        Account account = new Account(email, firstName, lastName, new AccountEventPublisherService(eventBus));
        for (char c = 'a'; c <= 'z'; c++) {
            account.changeLastName(c+"");
        }
        account = eventStore.account(email);
        assertEquals("z", account.user().lastName());
    }
}