package se.omegapoint.academy.opmarketplace.customer.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;

import java.io.IOException;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void should_save_account() throws IOException, InterruptedException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        accountRepository.append(Account.createAccount(new AccountCreationRequested(email, user)));
        Account account = accountRepository.account(email).get();
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }
    @Test
    public void testAccountInExistence() throws Exception {
        Email email = new Email("exist@exist.com");
        User user = new User("exist", "exist");
        accountRepository.append(Account.createAccount(new AccountCreationRequested(email, user)));
        assertTrue(accountRepository.accountInExistence(email));
    }

    @Test
    public void should_change_last_name_to_z() throws IOException, InterruptedException {
        Email email = new Email("change@change.com");
        User user = new User("initial", "initial");
        accountRepository.append(Account.createAccount(new AccountCreationRequested(email, user)));
        Account account = accountRepository.account(email).get();
        for (char c = 'a'; c <= 'z'; c++) {
            accountRepository.append(account.changeUser("initial", c+""));
            Thread.sleep(1);
        }

        account = accountRepository.account(email).get();
        assertEquals("z", account.user().lastName());
    }
}