package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisher;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountRepository;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@ActiveProfiles("test")
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void should_create_account() throws IOException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        Account account = new Account(Arrays.asList(new DomainEvent[] {Account.requestAccount(new AccountRequested(email, user))}));
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }

    @Test
    public void should_save_account() throws IOException, InterruptedException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        accountRepository.append(Account.requestAccount(new AccountRequested(email, user)));
        Account account = accountRepository.account(email).value();
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }

    @Test
    public void should_change_last_name_to_z() throws IOException, InterruptedException {
        Email email = new Email("change@change.com");
        User user = new User("initial", "initial");
        accountRepository.append(Account.requestAccount(new AccountRequested(email, user)));
        Account account = accountRepository.account(email).value();
        for (char c = 'a'; c <= 'z'; c++) {
            accountRepository.append(account.changeUser("initial", c+""));
            Thread.sleep(1);
        }

        account = accountRepository.account(email).value();
        assertEquals("z", account.user().lastName());
    }
}