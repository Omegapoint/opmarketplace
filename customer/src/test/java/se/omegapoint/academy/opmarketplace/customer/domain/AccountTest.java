package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@ActiveProfiles("test")
public class AccountTest {

    @Autowired
    private AccountRepository eventStore;

    @Autowired
    private AccountEventPublisher accountEventPublisher;

    @Test
    public void should_create_account(){
        String email = "create@create.com";
        String firstName = "createFirst";
        String lastName = "createLast";
        Account account = new Account(new Email(email), new User(firstName, lastName), accountEventPublisher);
        assertEquals(email, account.email().address());
        assertEquals(email, account.id());
        assertEquals(firstName, account.user().firstName());
        assertEquals(lastName, account.user().lastName());
    }

    @Test
    public void should_save_account() throws IOException, InterruptedException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        new Account(email, user, accountEventPublisher);
        long timeout = System.currentTimeMillis() + 500;
        while (!eventStore.accountInExistence(email).value() && System.currentTimeMillis() < timeout){}
        Account account = eventStore.account(email).value();
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }

    @Test
    public void should_change_last_name_to_z() throws IOException, InterruptedException {
        String email = "change@change.com";
        String firstName = "initial";
        String lastName = "initial";
        Account account = new Account(new Email(email), new User(firstName, lastName), accountEventPublisher);
        for (char c = 'a'; c <= 'z'; c++) {
            account.changeUser("initial", c+"");
            Thread.sleep(1);
        }
        // Wait to make sure all events are received by eventStore.
        Thread.sleep(20);
        account = eventStore.account(new Email(email)).value();
        assertEquals("z", account.user().lastName());
    }
}