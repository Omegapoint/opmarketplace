package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
public class AccountTest {

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
    public void should_change_last_name_to_z() throws IOException, InterruptedException {
        Email email = new Email("change@change.com");
        User user = new User("initial", "initial");
        Account account = new Account(Arrays.asList(new DomainEvent[] {Account.requestAccount(new AccountRequested(email, user))}));
        for (char c = 'a'; c <= 'z'; c++) {
            account.changeUser("initial", c+"");
        }
        assertEquals("z", account.user().lastName());
    }
}