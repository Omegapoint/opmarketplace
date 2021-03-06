package json.customer;

import json.Requests;
import json.Result;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class CreateAccountRequest {
    public final Result email;
    public final Result firstName;
    public final Result lastName;

    public CreateAccountRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("CreateAccountRequest Validation Started");
        this.email = email(values, mockMvc);
        this.firstName = firstName(values, mockMvc);
        this.lastName = lastName(values, mockMvc);
        System.out.println("CreateAccountRequest Validation Ended");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createUser(s, "valid", "valid", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result firstName(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createUser("valid@valid.com", s, "valid", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result lastName(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.createUser("valid@valid.com", "valid", s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
