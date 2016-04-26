package json.marketplace;

import com.fasterxml.jackson.core.JsonParseException;
import json.Requests;
import json.Result;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class ChangeItemRequest {
    public final Result id;
    public final Result title;
    public final Result description;
    public final Result price;
    public final Result supply;
    public final Result seller;

    public ChangeItemRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("ChangeItemRequest Validation Started");
        this.id = id(values, mockMvc);
        this.title = title(values, mockMvc);
        this.description = description(values, mockMvc);
        this.price = price(values, mockMvc);
        this.supply = supply(values, mockMvc);
        this.seller = seller(values, mockMvc);
        System.out.println("ChangeItemRequest Validation Ended");
    }

    private Result id(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeItem(s, "valid", "valid", "1", "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result title(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", s, "valid", "1", "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result description(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", "valid", s, "1", "1", "valid@valid.com", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result price(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.changeItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", "valid", "valid", s, "1", "valid@valid.com", mockMvc)
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
                MvcResult mvcResult = Requests.changeItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", "valid", "valid", "1", s, "valid@valid.com", mockMvc)
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
                try{
                    MvcResult mvcResult = Requests.changeItem("38400000-8cf0-11bd-b23e-10b96e4ef00d", "valid", "valid", "1", "1", s, mockMvc)
                            .andReturn();
                    res.registerResult(mvcResult, s);
                } catch (AuthenticationCredentialsNotFoundException e){
                    res.registerBlock();
                }
            }
        return res;
    }
}
