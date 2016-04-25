import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountCreditDepositRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountUserChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemPurchaseRequestedDTO;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class TestRequests {

    public static ResultActions createItem(String title, String description, int price, int quantity, String seller, MockMvc mockMvc) throws Exception {
        String content = itemCreationJson(title, description, price, quantity, seller);
        MvcResult mvcResult = mockMvc.perform(post("/items")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions getItem(String id, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items")
                .contentType(APPLICATION_JSON)
                .param("id", id)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions getItemAuth(String id, String email, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items")
                .contentType(APPLICATION_JSON)
                .header("email", email)
                .param("id", id)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private static String itemCreationJson(String title, String description, int price, int quantity, String seller) throws Exception {
        String content = "{" +
                    "\"title\":\"" + title + "\"," +
                    "\"description\":\"" + description + "\"," +
                    "\"price\":" + price + "," +
                    "\"supply\":" + quantity + "," +
                    "\"seller\":\"" + seller + "\"" +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemCreationRequestedDTO.class);
        return content;
    }

    public static ResultActions searchItems(String query, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items/search/")
                .param("query", query)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions changeItem(String id, String title, String description, int price, int quantity, MockMvc mockMvc) throws Exception {
        String content = itemChangeJson(id, title, description, price, quantity);
        MvcResult mvcResult = mockMvc.perform(put("/items")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private static String itemChangeJson(String id, String title, String description, int price, int quantity) throws Exception {
        String content =
                "{" +
                    "\"id\":\"" + id + "\"," +
                    "\"title\":\"" + title + "\"," +
                    "\"description\":\"" + description + "\"," +
                    "\"price\":" + price + "," +
                    "\"supply\":" + quantity +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemChangeRequestedDTO.class);
        return content;
    }

    public static ResultActions purchaseItem(String itemId, int quantity, String buyerId, MockMvc mockMvc) throws Exception {
        String content = itemPurchaseRequestJson(itemId, quantity, buyerId);
        MvcResult mvcResult = mockMvc.perform(post("/items/purchase")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private static String itemPurchaseRequestJson(String itemId, int quantity, String buyerId) throws Exception {
        String content =
                "{" +
                    "\"itemId\":\"" + itemId + "\"," +
                    "\"quantity\":" + quantity + "," +
                    "\"buyerId\":\"" + buyerId + "\"" +
                "}";
        // Validate content
        new ObjectMapper().readValue(content, ItemPurchaseRequestedDTO.class);
        return content;
    }

    public static String userJson(String email, String firstName, String lastName) throws Exception {
        String content =
                "{" +
                    "\"email\":\"" + email + "\"," +
                    "\"user\":{" +
                        "\"firstName\":\"" + firstName + "\"," +
                        "\"lastName\":\"" + lastName + "\"" +
                    "}" +
                "}";
        new ObjectMapper().readValue(content, AccountUserChangeRequestedDTO.class);
        return content;
    }

    public static ResultActions deleteUser(String email, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();


        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions createUser(String email, String firstName, String lastName, MockMvc mockMvc) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(post("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions getUser(String email, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions changeUser(String email, String firstName, String lastName, MockMvc mockMvc) throws Exception {
        String content = userJson(email, firstName, lastName);
        MvcResult mvcResult = mockMvc.perform(put("/accounts")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions depositCredit(String email, int credit, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/accounts/credit/deposit?credit=" + credit).with(httpBasic(email, ""))
                .contentType(APPLICATION_JSON)
                .content("")
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions withdrawCredit(String email, int credit, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/accounts/credit/withdraw?credit=" + credit).with(httpBasic(email, ""))
                .contentType(APPLICATION_JSON)
                .content("")
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

}
