package se.omegapoint.academy.opmarketplace.customer.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.Event;
import se.omegapoint.academy.opmarketplace.customer.Application;
import se.omegapoint.academy.opmarketplace.customer.TestConfiguration;
import se.omegapoint.academy.opmarketplace.customer.domain.events.*;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountDeleted;
import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.OrderDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.UserDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.external_event.*;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
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

    @Test
    public void should_create_account() throws Exception {
        addUser("test1@email.com", "first", "last");
        assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_duplicate() throws Exception {
        addUser("test2@email.com", "first", "last");
        addUser("test2@email.com", "first", "last");
        assertEquals(1, testPublisher.seenEvents(AccountCreated.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountNotCreated.class.getName()));
    }

    @Test
    public void should_not_add_account_due_to_ill_formed_email() throws Exception {
        addUser("@email.com", "first", "last");
        assertEquals(0, testPublisher.seenEvents(AccountCreated.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountNotCreated.class.getName()));
    }

    @Test
    public void should_retrieve_account() throws Exception {
        addUser("test3@email.com", "first", "last");
        getUser("test3@email.com");
        assertEquals(1, testPublisher.seenEvents(AccountObtained.class.getName()));
    }

    @Test
    public void should_not_retrieve_non_existing_account() throws Exception {
        getUser("test4@email.com");
        assertEquals(0, testPublisher.seenEvents(AccountObtained.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountNotObtained.class.getName()));
    }

    @Test
    public void should_not_retrieve_account_due_to_ill_formed_email() throws Exception {
        addUser("@email.com", "first", "last");
        getUser("@email.com");
        assertEquals(0, testPublisher.seenEvents(AccountObtained.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountNotObtained.class.getName()));
    }

    @Test
    public void should_change_user() throws Exception {
        addUser("test5@email.com", "first", "last");
        changeUser("test5@email.com", "changed", "last");
        assertEquals(1, testPublisher.seenEvents(AccountUserChanged.class.getName()));
    }

    @Test
    public void should_not_change_non_existing_user() throws Exception {
        changeUser("test6@email.com", "changed", "last");
        assertEquals(0, testPublisher.seenEvents(AccountUserChanged.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountUserNotChanged.class.getName()));
    }

    @Test
    public void should_not_change_user_due_to_ill_formed_email() throws Exception {
        addUser("@email.com", "first", "last");
        changeUser("6@email.com", "changed", "last");
        assertEquals(0, testPublisher.seenEvents(AccountUserChanged.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountUserNotChanged.class.getName()));
    }

    @Test
    public void should_delete_user() throws Exception {
        addUser("test7@email.com", "first", "last");
        deleteUser("test7@email.com");
        assertEquals(1, testPublisher.seenEvents(AccountDeleted.class.getName()));
    }

    @Test
    public void should_not_delete_non_existing_user() throws Exception {
        deleteUser("test8@email.com");
        assertEquals(1, testPublisher.seenEvents(AccountNotDeleted.class.getName()));
    }

    @Test
    public void should_not_delete_already_deleted_user() throws Exception {
        addUser("test9@email.com", "first", "last");
        deleteUser("test9@email.com");
        deleteUser("test9@email.com");
        assertEquals(1, testPublisher.seenEvents(AccountDeleted.class.getName()));
        assertEquals(1, testPublisher.seenEvents(AccountNotDeleted.class.getName()));
    }

    @Test
    public void should_not_delete_non_user_with_ill_formatted_email() throws Exception {
        deleteUser("@email.com");
        assertEquals(1, testPublisher.seenEvents(AccountNotDeleted.class.getName()));
    }

    @Test
    public void should_deposit_credit_to_user() throws Exception {
        addUser("deposit@credit.com", "first", "last");
        depositCredits("deposit@credit.com", 10);
        depositCredits("deposit@credit.com", 20);
        getUser("deposit@credit.com");
        assertEquals(30, ((AccountObtained)testPublisher.getLastEvent()).account().vault().amount());
    }

    @Test
    public void should_withdraw_credit_from_user() throws Exception {
        addUser("withdraw@credit.com", "first", "last");
        depositCredits("withdraw@credit.com", 10);
        depositCredits("withdraw@credit.com", 20);
        withdrawCredits("withdraw@credit.com", 10);
        withdrawCredits("withdraw@credit.com", 20);
        getUser("withdraw@credit.com");
        assertEquals(0, ((AccountObtained)testPublisher.getLastEvent()).account().vault().amount());
    }

    @Test
    public void should_perform_credit_transaction(){
        String buyer = "buyer@market.com";
        String seller = "seller@market.com";
        se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event request = new AccountCreationRequestedDTO("1", buyer, new UserDTO("buyer", "market"));
        accountService.accept(Event.wrap(request));

        request = new AccountCreationRequestedDTO("1", seller, new UserDTO("seller", "market"));
        accountService.accept(Event.wrap(request));

        request = new AccountCreditDepositRequestedDTO("1", buyer, 10);
        accountService.accept(Event.wrap(request));

        request = new ItemOrderedDTO("1", new OrderDTO(UUID.randomUUID().toString(), 10, seller, buyer));
        accountService.accept(Event.wrap(request));
        ItemPaymentCompleted completed = (ItemPaymentCompleted) testPublisher.getLastEvent();
        assertEquals(10, completed.price().amount());
        assertEquals(seller, completed.sellerId().address());
        assertEquals(buyer, completed.buyerId().address());

        request = new AccountRequestedDTO("1", seller);
        accountService.accept(Event.wrap(request));
        AccountObtained sellerAccount = (AccountObtained) testPublisher.getLastEvent();
        assertEquals(10, sellerAccount.account().vault().amount());

        request = new AccountRequestedDTO("1", buyer);
        accountService.accept(Event.wrap(request));
        AccountObtained buyerAccount = (AccountObtained) testPublisher.getLastEvent();
        assertEquals(0, buyerAccount.account().vault().amount());
    }

    @Test
    public void should_not_perform_credit_transaction_due_to_buyer_insufficient_funds(){
        String buyer = "poorbuyer@market.com";
        String seller = "sadseller@market.com";
        se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event request = new AccountCreationRequestedDTO("1", buyer, new UserDTO("buyer", "market"));
        accountService.accept(Event.wrap(request));

        request = new AccountCreationRequestedDTO("1", seller, new UserDTO("seller", "market"));
        accountService.accept(Event.wrap(request));

        request = new ItemOrderedDTO("1", new OrderDTO(UUID.randomUUID().toString(), 10, seller, buyer));
        accountService.accept(Event.wrap(request));
        ItemPaymentNotCompleted notCompleted = (ItemPaymentNotCompleted) testPublisher.getLastEvent();
        assertEquals("Insufficient funds.", notCompleted.reason());

        request = new AccountRequestedDTO("1", seller);
        accountService.accept(Event.wrap(request));
        AccountObtained sellerAccount = (AccountObtained) testPublisher.getLastEvent();
        assertEquals(0, sellerAccount.account().vault().amount());

        request = new AccountRequestedDTO("1", buyer);
        accountService.accept(Event.wrap(request));
        AccountObtained buyerAccount = (AccountObtained) testPublisher.getLastEvent();
        assertEquals(0, buyerAccount.account().vault().amount());
    }

    private void addUser(String email, String firstName, String lastName) throws IOException {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email +"\"," +
                "\"user\":{\"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\"}}";

        AccountCreationRequestedDTO model = objectMapper.readValue(inputData, AccountCreationRequestedDTO.class);
        accountService.accept(Event.wrap(model));
    }

    private void getUser(String email) throws IOException {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email + "\"}";

        AccountRequestedDTO accountRequestedDTO = objectMapper.readValue(inputData, AccountRequestedDTO.class);
        accountService.accept(Event.wrap(accountRequestedDTO));
    }

    private void changeUser(String email, String firstName, String lastName) throws IOException {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email +"\"," +
                "\"user\":{\"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\"}}";

        AccountUserChangeRequestedDTO accountUserChangeRequestedDTO = objectMapper.readValue(inputData, AccountUserChangeRequestedDTO.class);
        accountService.accept(Event.wrap(accountUserChangeRequestedDTO));
    }

    private void deleteUser(String email) throws Exception {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email + "\"}";

        AccountDeletionRequestedDTO accountDeletionRequestedDTO = objectMapper.readValue(inputData, AccountDeletionRequestedDTO.class);
        accountService.accept(Event.wrap(accountDeletionRequestedDTO));
    }

    private void depositCredits(String email, int credit) throws IOException {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email +"\"," +
                "\"credit\":" + credit + "}";

        AccountCreditDepositRequestedDTO model = objectMapper.readValue(inputData, AccountCreditDepositRequestedDTO.class);
        accountService.accept(Event.wrap(model));
    }

    private void withdrawCredits(String email, int credit) throws IOException {
        String inputData = "{\"requestId\":\"abc\",\"email\":\"" + email +"\"," +
                "\"credit\":" + credit + "}";

        AccountCreditWithdrawalRequestedDTO model = objectMapper.readValue(inputData, AccountCreditWithdrawalRequestedDTO.class);
        accountService.accept(Event.wrap(model));
    }
}