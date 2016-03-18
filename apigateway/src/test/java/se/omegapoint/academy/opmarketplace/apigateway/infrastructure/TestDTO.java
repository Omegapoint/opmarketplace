package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDTO {

    private ObjectMapper json;

    @Before
    public void setUp(){
        this.json = new ObjectMapper();
    }

    @Test
    public void testEmailDTODeserialization() throws Exception{
        EmailDTO dto = json.readValue("{\n" +
                "   \"address\":\"test@test.com\"\n" +
                "}", EmailDTO.class);
        assertEquals("test@test.com", dto.address);
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
        AccountDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"user\":{\n" +
                "       \"firstName\":\"first\",\n" +
                "       \"lastName\":\"last\"\n" +
                "   }\n" +
                "}", AccountDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountCreatedDTODeserialization() throws Exception{
        AccountCreatedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   }\n" +
                "}", AccountCreatedDTO.class);
        assertEquals("test@test.com", dto.email.address);
    }

    @Test
    public void testAccountCreationRequestedDTODeserialization() throws Exception{
        AccountCreationRequestedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"user\":{\n" +
                "       \"firstName\":\"first\",\n" +
                "       \"lastName\":\"last\"\n" +
                "   }\n" +
                "}", AccountCreationRequestedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountDeletedDTODeserialization() throws Exception{
        AccountDeletedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   }\n" +
                "}", AccountDeletedDTO.class);
        assertEquals("test@test.com", dto.email.address);
    }

    @Test
    public void testAccountDeletionRequestedDTODeserialization() throws Exception{
        AccountDeletionRequestedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   }\n" +
                "}", AccountDeletionRequestedDTO.class);
        assertEquals("test@test.com", dto.email.address);
    }

    @Test
    public void testAccountNotCreatedDTODeserialization() throws Exception{
        AccountNotCreatedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"reason\":\"reason\"\n" +
                "}", AccountNotCreatedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountNotDeletedDTODeserialization() throws Exception{
        AccountNotDeletedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"reason\":\"reason\"\n" +
                "}", AccountNotDeletedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountNotObtainedDTODeserialization() throws Exception{
        AccountNotObtainedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"reason\":\"reason\"\n" +
                "}", AccountNotObtainedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("reason", dto.reason);
    }

    @Test
    public void testAccountObtainedDTODeserialization() throws Exception{
        AccountObtainedDTO dto = json.readValue("{\n" +
                "   \"account\":{\n" +
                "      \"email\":{\n" +
                "           \"address\":\"test@test.com\"\n" +
                "       },\n" +
                "       \"user\":{\n" +
                "           \"firstName\":\"first\",\n" +
                "           \"lastName\":\"last\"\n" +
                "       }\n" +
                "   }" +
                "}", AccountObtainedDTO.class);
        assertEquals("test@test.com", dto.account.email.address);
        assertEquals("first", dto.account.user.firstName);
        assertEquals("last", dto.account.user.lastName);
    }

    @Test
    public void testAccountRequestedDTODeserialization() throws Exception{
        AccountRequestedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   }\n" +
                "}", AccountRequestedDTO.class);
        assertEquals("test@test.com", dto.email.address);
    }

    @Test
    public void testAccountUserChangedDTODeserialization() throws Exception{
        AccountUserChangedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   }\n" +
                "}", AccountUserChangedDTO.class);
        assertEquals("test@test.com", dto.email.address);
    }

    @Test
    public void testAccountUserChangeRequestedDTODeserialization() throws Exception{
        AccountUserChangeRequestedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"user\":{\n" +
                "       \"firstName\":\"first\",\n" +
                "       \"lastName\":\"last\"\n" +
                "   }\n" +
                "}", AccountUserChangeRequestedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("first", dto.user.firstName);
        assertEquals("last", dto.user.lastName);
    }

    @Test
    public void testAccountUserNotChangedDTODeserialization() throws Exception{
        AccountUserNotChangedDTO dto = json.readValue("{\n" +
                "   \"email\":{\n" +
                "       \"address\":\"test@test.com\"\n" +
                "   },\n" +
                "   \"reason\":\"reason\"\n" +
                "}", AccountUserNotChangedDTO.class);
        assertEquals("test@test.com", dto.email.address);
        assertEquals("reason", dto.reason);
    }

}
