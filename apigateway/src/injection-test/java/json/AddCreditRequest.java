package json;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class AddCreditRequest {

    public final Result email;
    public final Result credit;

    public AddCreditRequest(List<String> values, MockMvc mockMvc) throws Exception {
        System.out.println("AddCreditRequest Validation Started");
        this.email = email(values, mockMvc);
        this.credit = credit(values, mockMvc);
        System.out.println("AddCreditRequest Validation Ended");
    }

    private Result email(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            MvcResult mvcResult = Requests.addCredit(s, "1", mockMvc)
                    .andReturn();
            res.registerResult(mvcResult, s);
        }
        return res;
    }

    private Result credit(List<String> values, MockMvc mockMvc) throws Exception {
        Result res = new Result(values.size());
        for (String s : values) {
            try {
                MvcResult mvcResult = Requests.addCredit("valid@valid.com", s, mockMvc)
                        .andReturn();
                res.registerResult(mvcResult, s);
            } catch (JsonParseException e){
                res.registerBlock();
            }
        }
        return res;
    }
}
