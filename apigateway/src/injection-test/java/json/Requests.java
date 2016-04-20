package json;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountCreditDepositRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.account.AccountUserChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemChangeRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item.ItemPurchaseRequestedDTO;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Requests {

    private static ObjectMapper json = new ObjectMapper();

    public static int getAverageResponseTimeForSearch(int num_searches, int num_search_terms, List<String> dictionary, MockMvc mockMvc) throws Exception {
        Random random = new Random();
        int responseTime = 0;
        for (int i = 0; i < num_searches; i++) {
            String query = "";
            for (int j = 0; j < num_search_terms; j++) {
                query = query + " " + dictionary.get(random.nextInt(1000));
            }
            long start = System.currentTimeMillis();
            searchItems(query, mockMvc)
                    .andExpect(status().isOk());
            responseTime += System.currentTimeMillis() - start;
        }
        return responseTime/num_searches;
    }

    public static ResultActions createItem(String title, String description, String price, String quantity, String seller, MockMvc mockMvc) throws Exception {
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

    public static ResultActions searchItems(String query, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/items/search/")
                .param("query", query)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    public static ResultActions changeItem(String id, String title, String description, String price, String quantity, MockMvc mockMvc) throws Exception {
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

    public static ResultActions purchaseItem(String itemId, String quantity, String buyerId, MockMvc mockMvc) throws Exception {
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

    public static ResultActions getUser(String email, MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts")
                .param("email", email)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
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

    public static ResultActions addCredit(String email, String credit, MockMvc mockMvc) throws Exception {
        String content = creditJson(email, credit);
        MvcResult mvcResult = mockMvc.perform(put("/accounts/credit/deposit")
                .contentType(APPLICATION_JSON)
                .content(content)
        )
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();

        return mockMvc.perform(asyncDispatch(mvcResult));
    }

    private static String itemChangeJson(String id, String title, String description, String price, String quantity) throws Exception {
        String content =
                "{" +
                        "\"id\":\"" + id + "\"," +
                        "\"title\":\"" + title + "\"," +
                        "\"description\":\"" + description + "\"," +
                        "\"price\":" + price + "," +
                        "\"supply\":" + quantity +
                        "}";
        // Validate content
        json.readValue(content, ItemChangeRequestedDTO.class);
        return content;
    }

    private static String itemCreationJson(String title, String description, String price, String quantity, String seller) throws Exception {
        String content = "{" +
                "\"title\":\"" + title + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"price\":" + price + "," +
                "\"supply\":" + quantity + "," +
                "\"seller\":\"" + seller + "\"" +
                "}";
        // Validate content
        json.readValue(content, ItemCreationRequestedDTO.class);
        return content;
    }


    private static String itemPurchaseRequestJson(String itemId, String quantity, String buyerId) throws Exception {
        String content =
                "{" +
                        "\"itemId\":\"" + itemId + "\"," +
                        "\"quantity\":" + quantity + "," +
                        "\"buyerId\":\"" + buyerId + "\"" +
                        "}";
        // Validate content
        json.readValue(content, ItemPurchaseRequestedDTO.class);
        return content;
    }

    private static String creditJson(String email, String credit) throws Exception {
        String content =
                "{" +
                        "\"email\":\"" + email + "\"," +
                        "\"credit\":" + credit +
                        "}";
        json.readValue(content, AccountCreditDepositRequestedDTO.class);
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
        json.readValue(content, AccountUserChangeRequestedDTO.class);
        return content;
    }

    public static boolean isBlock(MvcResult result) throws IOException {
        int status = result.getResponse().getStatus();
        if (status >= 400 && status < 500){
            return json.readValue(result.getResponse().getContentAsString(), ReasonExtractor.class).isValidationFailure();
        }
        return false;
    }
}
