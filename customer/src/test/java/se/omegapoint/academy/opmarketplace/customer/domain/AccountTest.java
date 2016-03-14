package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories.AccountFactory;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
public class AccountTest {

    @Test
    public void should_create_account() throws IOException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        Account account = AccountFactory.fromDomainEvents(
                Arrays.asList(new DomainEvent[] {Account.createAccount(new AccountRequested(email, user))}));
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }

    @Test
    public void should_return_correct_AccountUserChanged_event() throws IOException, InterruptedException {
        Email email = new Email("change@change.com");
        User user = new User("initial", "initial");
        Account account = AccountFactory.fromDomainEvents(
                Arrays.asList(new DomainEvent[] {Account.createAccount(new AccountRequested(email, user))}));
        AccountUserChanged accountUserChanged = account.changeUser("a", user.lastName());

        assertEquals("a", accountUserChanged.user().firstName());
    }
}