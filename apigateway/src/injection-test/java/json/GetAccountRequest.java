package json;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class GetAccountRequest {

    public final Result email;

    public GetAccountRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("GetAccountRequest Validation Started");
        this.email = email(values, mockMvc);
        System.out.println("GetAccountRequest Validation Ended");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.getUser(s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
