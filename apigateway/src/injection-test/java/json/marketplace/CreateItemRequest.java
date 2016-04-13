package json.marketplace;

import com.fasterxml.jackson.core.JsonParseException;
import json.Requests;
import json.Result;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class CreateItemRequest {

    public final Result title;
    public final Result description;
    public final Result price;
    public final Result supply;
    public final Result seller;

    public CreateItemRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("CreateItemRequest Validation Started");
        this.title = title(values, mockMvc);
        this.description = description(values, mockMvc);
        this.price = price(values, mockMvc);
        this.supply = supply(values, mockMvc);
        this.seller = seller(values, mockMvc);
        System.out.println("CreateItemRequest Validation Ended");
    }

    private Result title(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createItem(s, "valid", "1", "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result description(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createItem("valid", s, "1", "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result price(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.createItem("valid", "valid", s, "1", "valid@valid.com", mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (JsonParseException e){
                res.registerBlock();
            }
        }
        return res;
    }

    private Result supply(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.createItem("valid", "valid", "1", s, "valid@valid.com", mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (JsonParseException e){
                res.registerBlock();
            }
        }
        return res;
    }

    private Result seller(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createItem("valid", "valid", "1", "1", s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
