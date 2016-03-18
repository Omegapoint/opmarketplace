package se.omegapoint.academy.opmarketplace.customer.domain;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreationRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChangeRequested;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.factories.AccountFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
public class AccountTest {

    @Test
    public void should_create_account() throws IOException {
        Email email = new Email("create@create.com");
        User user = new User("createFirst", "createLast");
        Account account = AccountFactory.fromPersistableEvents(
                Arrays.asList(new PersistableEvent[] {Account.createAccount(new AccountCreationRequested(email, user, new Timestamp(1)))}));
        assertEquals(email.address(), account.email().address());
        assertEquals(email.address(), account.id());
        assertEquals(user.firstName(), account.user().firstName());
        assertEquals(user.lastName(), account.user().lastName());
    }

    @Test
    public void should_return_correct_AccountUserChanged_event() throws IOException, InterruptedException {
        Email email = new Email("change@change.com");
        User user = new User("initial", "initial");
        Account account = AccountFactory.fromPersistableEvents(
                Arrays.asList(new PersistableEvent[] {Account.createAccount(new AccountCreationRequested(email, user, new Timestamp(1)))}));

        User newUser = new User("a", user.lastName());
        AccountUserChangeRequested changeRequest = new AccountUserChangeRequested(email, newUser, new Timestamp(1));
        AccountUserChanged accountUserChanged = account.changeUser(changeRequest);

        assertEquals("a", accountUserChanged.user().firstName());
    }
}