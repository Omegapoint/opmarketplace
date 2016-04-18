package se.omegapoint.academy.opmarketplace.customer.infrastructure.data_extraction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.academy.opmarketplace.customer.Application;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditWithdrawn;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.User;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.AccountEventStore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UserDataShortcutTest {

    @Autowired
    AccountEventStore accountEventStore;

    @Autowired
    UserDataShortcut userDataShortcut;

    @Test
    public void testGetMembersSince() throws Exception {
        createUser("user_one@email.com", "firstName", "lastName", LocalDateTime.now());
        createUser("user_two@email.com", "firstName", "lastName", LocalDateTime.now().minusDays(10));

        assertArrayEquals(new String[]{"user_two@email.com"}, userDataShortcut.getMembersSince(Timestamp.valueOf(LocalDateTime.now().minusDays(9))).toArray());
        assertEquals(0, userDataShortcut.getMembersSince(Timestamp.valueOf(LocalDateTime.now().minusDays(11))).size());
    }

    @Test
    public void testFilterOnPurchases() throws Exception {
        String fredrik = "fredrik@email.com"; String bo = "bo@email.com";
        createUser(fredrik, "firstName", "lastName", LocalDateTime.now());
        createUser(bo, "firstName", "lastName", LocalDateTime.now());

        withdrawCredits(fredrik, 100);

        assertArrayEquals(
                new String[]{fredrik},
                userDataShortcut.filterOnPurchases(Arrays.asList(fredrik, bo), 90).toArray());
}

    private void createUser(String email, String firstName, String lastName, LocalDateTime time) {
        PersistableEvent event = new AccountCreated(
                new Email(email),
                new User(firstName, lastName),
                Timestamp.valueOf(time));

        accountEventStore.append(event);
    }

    private void withdrawCredits(String email, int credits) {
        PersistableEvent event = new AccountCreditWithdrawn(
                new Email(email),
                new Credit(credits));

        accountEventStore.append(event);

    }
}