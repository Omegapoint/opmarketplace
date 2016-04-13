package json.customer;

import json.Requests;
import json.Result;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class ChangeUserRequest {

    public final Result email;
    public final Result firstName;
    public final Result lastName;

    public ChangeUserRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("ChangeUserRequest Validation Started");
        this.email = email(values, mockMvc);
        this.firstName = firstName(values, mockMvc);
        this.lastName = lastName(values, mockMvc);
        System.out.println("ChangeUserRequest Validation Started");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeUser(s, "valid", "valid", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result firstName(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeUser("valid@valid.com", s, "valid", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result lastName(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.changeUser("valid@valid.com", "valid", s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
