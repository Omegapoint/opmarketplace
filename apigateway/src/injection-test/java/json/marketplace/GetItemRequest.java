package json.marketplace;

import json.Requests;
import json.Result;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class GetItemRequest {

    public final Result id;

    public GetItemRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("GetItemRequest Validation Started");
        this.id = id(values, mockMvc);
        System.out.println("GetItemRequest Validation Ended");
    }

    private Result id(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.getItem(s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
