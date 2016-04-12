package json;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class DeleteAccountRequest {
    public final Result email;

    public DeleteAccountRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("DeleteAccountRequest Validation Started");
        this.email = email(values, mockMvc);
        System.out.println("DeleteAccountRequest Validation Ended");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.deleteUser(s, mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }
}
