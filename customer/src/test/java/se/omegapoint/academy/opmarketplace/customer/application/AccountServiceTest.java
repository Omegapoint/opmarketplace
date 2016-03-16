package se.omegapoint.academy.opmarketplace.customer.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.Event;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.TestConfiguration;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations.AccountUserChangeRequestedModel;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestConfiguration.TestPublisher testPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception{
        testPublisher.clear();
    }

    // TODO: 15/03/16 Maybe check that the account has actually been created?
    @Test
    public void should_create_account() throws Exception {
        addUser("test1@email.com", "first", "last");
        Assert.assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_duplicate() throws Exception {
        addUser("test2@email.com", "first", "last");
        addUser("test2@email.com", "first", "last");
        Assert.assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountNotCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_ill_formed_email() throws Exception {
        addUser("@email.com", "first", "last");
        Assert.assertEquals(0, testPublisher.seenEvents(AccountCreated.class.getName()));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountNotCreated.class.getName()));
    }

    @Test
    public void should_retrieve_account() throws Exception {
        addUser("test3@email.com", "first", "last");
        getUser("test3@email.com");
        Assert.assertEquals(1, testPublisher.seenEvents(AccountObtained.class.getName()));
    }

    @Test
    public void should_not_retrieve_non_existing_account() throws Exception {
        getUser("test4@email.com");
        Assert.assertEquals(0, testPublisher.seenEvents(AccountObtained.class.getName()));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountNotObtained.class.getName()));
    }

    @Test
    public void should_not_retrieve_account_due_to_ill_formed_email() throws Exception {
        addUser("@email.com", "first", "last");
        getUser("@email.com");
        Assert.assertEquals(0, testPublisher.seenEvents(AccountObtained.class.getName()));
        Assert.assertEquals(1, testPublisher.seenEvents(AccountNotObtained.class.getName()));
    }

    @Test
    public void should_change_user() throws Exception {
        addUser("test5@email.com", "first", "last");
        changeUser("test5@email.com", "changed", "last");
        Assert.assertEquals(1, testPublisher.seenEvents(AccountUserChanged.class.getName()));
    }

    @Test
    public void should_not_change_non_existing_user() throws Exception {
        changeUser("test6@email.com", "changed", "last");
        Assert.assertEquals(0, testPublisher.seenEvents(AccountUserChanged.class.getName()));
    }

    private void addUser(String email, String firstName, String lastName) throws IOException {
        String inputData = "{\"email\":{\"address\":\"" + email +"\"}," +
                "\"user\":{\"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\"}," +
                "\"timestamp\":123456789}";

        AccountCreationRequestedModel model = objectMapper.readValue(inputData, AccountCreationRequestedModel.class);
        accountService.accept(Event.wrap(model));
    }

    private void getUser(String email) throws IOException {
        String inputData = "{\"email\":{\"address\":\"" + email + "\"}," +
                "\"timestamp\":123456789}";

        AccountRequestedModel accountRequestedModel = objectMapper.readValue(inputData, AccountRequestedModel.class);
        accountService.accept(Event.wrap(accountRequestedModel));
    }

    private void changeUser(String email, String firstName, String lastName) throws IOException {
        String inputData = "{\"email\":{\"address\":\"" + email +"\"}," +
                "\"user\":{\"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\"}," +
                "\"timestamp\":123456789}";

        AccountUserChangeRequestedModel accountUserChangeRequestedModel = objectMapper.readValue(inputData, AccountUserChangeRequestedModel.class);
        accountService.accept(Event.wrap(accountUserChangeRequestedModel));
    }
}