package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountDeletionRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountUserChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.AccountDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.UserDTO;

import static org.junit.Assert.assertEquals;


public class TestDTO {

    private ObjectMapper json;

    @Before
    public void setUp(){
        this.json = new ObjectMapper();
    }


    @Test
    public void testUserDTODeserialization() throws Exception{
        UserDTO dto = json.readValue("{\n" +
                "   \"firstName\":\"first\",\n" +
                "   \"lastName\":\"last\"\n" +
                "}", UserDTO.class);
        assertEquals("first", dto.firstName);
        assertEquals("last", dto.lastName);
    }

    @Test
    public void testAccountDTODeserialization() throws Exception{
        AccountDTO dto = json.readValue("{" +
                "   \"email\":\"test@test.com\"," +
                "   \"user\":{" +
                "       \"firstName\":\"first\"," +
                "       \"lastName\":\"last\"" +
                "   }," +
                "   \"vault\":" + 10 +
                "}", AccountDTO.class);
        assertEquals("test@test.com", dto.email);
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountCreatedDTODeserialization() throws Exception{
        AccountCreatedDTO dto = json.readValue(
                "{" +
                "   \"requestId\":\"test@test.com\"" +
                "}", AccountCreatedDTO.class);
        assertEquals("test@test.com", dto.requestId());
    }

    @Test
    public void testAccountCreationRequestedDTODeserialization() throws Exception{
        AccountCreationRequestedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"email\":\"test@test.com\"," +
                "   \"user\":{\n" +
                "       \"firstName\":\"first\"," +
                "       \"lastName\":\"last\"" +
                "   }" +
                "}", AccountCreationRequestedDTO.class);
        assertEquals("test@test.com", dto.email);
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountDeletedDTODeserialization() throws Exception{
        AccountDeletedDTO dto = json.readValue("{\n" +
                "   \"requestId\":\"test@test.com\"\n" +
                "}", AccountDeletedDTO.class);
        assertEquals("test@test.com", dto.requestId());
    }

    @Test
    public void testAccountDeletionRequestedDTODeserialization() throws Exception{
        AccountDeletionRequestedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"email\":\"test@test.com\"" +
                "}", AccountDeletionRequestedDTO.class);
        assertEquals("test@test.com", dto.email);
    }

    @Test
    public void testAccountNotCreatedDTODeserialization() throws Exception{
        AccountNotCreatedDTO dto = json.readValue(
                "{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"reason\":\"reason\"" +
                "}", AccountNotCreatedDTO.class);
        assertEquals("test@test.com", dto.requestId());
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountNotDeletedDTODeserialization() throws Exception{
        AccountNotDeletedDTO dto = json.readValue(
                "{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"reason\":\"reason\"" +
                "}", AccountNotDeletedDTO.class);
        assertEquals("test@test.com", dto.requestId());
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountNotObtainedDTODeserialization() throws Exception{
        AccountNotObtainedDTO dto = json.readValue(
                "{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"reason\":\"reason\"" +
                "}", AccountNotObtainedDTO.class);
        assertEquals("test@test.com", dto.requestId());
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountObtainedDTODeserialization() throws Exception{
        AccountObtainedDTO dto = json.readValue(
                "{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"account\":{" +
                "      \"email\":\"test@test.com\"," +
                "      \"user\":{" +
                "           \"firstName\":\"first\"," +
                "           \"lastName\":\"last\"" +
                "       },\n" +
                "       \"vault\":" + 10 +
                "   }" +
                "}", AccountObtainedDTO.class);
        assertEquals("test@test.com", dto.account.email);
        assertEquals("first", dto.account.user.firstName);
        assertEquals("last", dto.account.user.lastName);
    }

    @Test
    public void testAccountRequestedDTODeserialization() throws Exception{
        AccountRequestedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"email\":\"test@test.com\"" +
                "}", AccountRequestedDTO.class);
        assertEquals("test@test.com", dto.requestId());
    }

    @Test
    public void testAccountUserChangedDTODeserialization() throws Exception{
        AccountUserChangedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"" +
                "}", AccountUserChangedDTO.class);
        assertEquals("test@test.com", dto.requestId());
    }

    @Test
    public void testAccountUserChangeRequestedDTODeserialization() throws Exception{
        AccountUserChangeRequestedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"email\":\"test@test.com\"," +
                "   \"user\":{" +
                "       \"firstName\":\"first\"," +
                "       \"lastName\":\"last\"" +
                "   }" +
                "}", AccountUserChangeRequestedDTO.class);
        assertEquals("test@test.com", dto.requestId());
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountUserNotChangedDTODeserialization() throws Exception{
        AccountUserNotChangedDTO dto = json.readValue("{" +
                "   \"requestId\":\"test@test.com\"," +
                "   \"reason\":\"reason\"" +
                "}", AccountUserNotChangedDTO.class);
        assertEquals("test@test.com", dto.requestId());
        assertEquals("reason", dto.reason);
    }

}
