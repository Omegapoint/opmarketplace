package json.marketplace;

import com.fasterxml.jackson.core.JsonParseException;
import json.Requests;
import json.Result;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class PurchaseItemRequest {
    public final Result itemId;
    public final Result quantity;
    public final Result buyerId;

    public PurchaseItemRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("PurchaseItemRequest Validation Started");
        this.itemId = itemId(values, mockMvc);
        this.quantity = quantity(values, mockMvc);
        this.buyerId = buyerId(values, mockMvc);
        System.out.println("PurchaseItemRequest Validation Ended");
    }

    private Result itemId(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.purchaseItem(s, "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }


    private Result quantity(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.purchaseItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", s, "valid@valid.com", mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (JsonParseException e){
                res.registerBlock();
            }
        }
        return res;
    }


    private Result buyerId(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.purchaseItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", "1", s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
